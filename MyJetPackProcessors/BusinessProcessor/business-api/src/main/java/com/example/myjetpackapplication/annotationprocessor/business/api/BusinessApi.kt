package com.example.myjetpackapplication.annotationprocessor.business.api

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import java.util.concurrent.ConcurrentSkipListSet

/**
 * Created by liutiantian on 2019-12-23 13:36 星期一
 */
object BusinessApi {
    private var businesses: MutableSet<BusinessItem> = ConcurrentSkipListSet<BusinessItem>()

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

    fun go(context: Context?, path: String) {
        findItem(path)?.let {
            go(context, it)
        }
    }

    fun go(context: Context?, item: BusinessItem) {
        context?.let {
            if (Activity::class.java.isAssignableFrom(item.targetClass)) {
                context.startActivity(Intent(context, item.targetClass))
            }
        }
    }

    fun findItem(path: String): BusinessItem? {
        return businesses.find { it.path == path }
    }

    fun getChildren(parent: BusinessItem?): List<BusinessItem>? {
        Log.i("112233", "getChildren, businesses.isEmpty(): ${businesses.isEmpty()}")
        if (businesses.isEmpty()) {
            return null
        }
        val res = mutableListOf<BusinessItem>()
        for (business in businesses) {
            Log.i("112233", "getChildren, business.title.isNullOrEmpty(): ${business.title.isNullOrEmpty()}")
            if (business.title.isNullOrEmpty()) {
                continue
            }
            Log.i("112233", "getChildren, business.path: ${business.path}; business.parent: ${business.parent}; parent.title: ${parent?.title}")
            if (business.parent == parent?.title || (business.parent.isNullOrEmpty() && parent?.title.isNullOrEmpty())) {
                res.add(business)
            }
        }
        return res
    }

    fun tryBack(current: BusinessItem?, layer: List<BusinessItem>? = null): List<BusinessItem>? {
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
                if (child.title == current?.title || (child.title.isNullOrEmpty() && current?.title.isNullOrEmpty())) {
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