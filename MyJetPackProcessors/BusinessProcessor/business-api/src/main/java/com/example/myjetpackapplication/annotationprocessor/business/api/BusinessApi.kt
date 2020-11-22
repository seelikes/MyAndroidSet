package com.example.myjetpackapplication.annotationprocessor.business.api

import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem

/**
 * Created by liutiantian on 2019-12-23 13:36 星期一
 */
object BusinessApi {
    private var businesses: MutableList<BusinessItem> = mutableListOf()

    fun addItem(item: BusinessItem) {
        synchronized(businesses) {
            if (businesses.contains(item)) {
                return
            }
            businesses.add(item)
        }
    }

    fun removeItem(item: BusinessItem) {
        synchronized(businesses) {
            businesses.remove(item)
        }
    }

    fun getChildren(parent: BusinessItem?): List<BusinessItem>? {
        if (businesses.isEmpty()) {
            return null
        }
        val res = mutableListOf<BusinessItem>()
        for (business in businesses) {
            if (business.parent == parent?.title) {
                res.add(business)
            }
        }
        return res
    }

    fun tryBack(current: BusinessItem, layer: List<BusinessItem>? = null): List<BusinessItem>? {
        if (businesses.isEmpty()) {
            return null
        }
        var currentLayer = layer
        if (currentLayer == null || currentLayer.isEmpty()) {
            currentLayer = businesses.filter { it.parent == null || it.parent.isEmpty() }
        }
        for (business in currentLayer) {
            val children = getChildren(business)
            if (children == null || children.isEmpty()) {
                continue
            }
            for (child in children) {
                if (child.title == current.title) {
                    return currentLayer
                }
                val guess = tryBack(current, children)
                if (guess != null && guess.isNotEmpty()) {
                    return guess
                }
            }
        }
        return null
    }
}