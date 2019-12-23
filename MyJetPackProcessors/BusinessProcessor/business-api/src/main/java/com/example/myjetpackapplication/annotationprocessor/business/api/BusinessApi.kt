package com.example.myjetpackapplication.annotationprocessor.business.api

import android.content.Context
import com.example.myjetpackapplication.annotationprocessor.business.annotation.ABusinessListAll
import com.example.myjetpackapplication.annotationprocessor.business.annotation.ABusinessManager
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.github.seelikes.android.dex.DexUtils
import com.java.lib.oil.GlobalMethods
import com.java.lib.oil.file.FileUtils
import com.java.lib.oil.json.JSON
import java.io.File

/**
 * Created by liutiantian on 2019-12-23 13:36 星期一
 */
object BusinessApi {
    internal lateinit var businesses: MutableList<BusinessItem>

    internal fun init(context: Context) {
        var BuildConfig =
            try {
                Class.forName(context.packageName + "." + "BuildConfig")
            }
            catch (e: ClassNotFoundException) {
                null
            }
        val DEBUG = BuildConfig?.getDeclaredField("DEBUG")?.get(null) as? Boolean ?: false
        val versionName = BuildConfig?.getDeclaredField("VERSION_CODE")?.get(null) as? String ?: ""
        val versionCode = BuildConfig?.getDeclaredField("VERSION_NAME")?.get(null) as? Int ?: 0

        val cache = File(context.cacheDir, "business/businesses.json")
        if (cache.exists()) {
            val record = JSON.parseObject(FileUtils.getInstance().readFileToString(cache), BusinessRecord::class.java)
            if (!DEBUG && record.versionName >= versionName && record.versionCode > versionCode) {
                businesses = record.business
                return
            }
        }
        DexUtils.with(context)
            .basePackage(context.packageName)
            .instantRun(DEBUG)
            .getClass {
                if (it == null) {
                    return@getClass
                }
                if (it.getAnnotation(ABusinessManager::class.java) != null) {
                    it.declaredMethods.forEach { method ->
                        if (method.getAnnotation(ABusinessListAll::class.java) != null) {
                            val businessItems = method.invoke(null) as? List<*>
                            if (businessItems == null || businessItems.isEmpty()) {
                                return@forEach
                            }
                            if (!BusinessApi::businesses.isInitialized) {
                                businesses = mutableListOf()
                            }
                            businesses.addAll(businessItems as List<BusinessItem>)
                        }
                    }
                }
            }
    }

    fun getChildren(parent: BusinessItem): List<BusinessItem>? {
        if (!BusinessApi::businesses.isInitialized) {
            return null
        }
        val res = mutableListOf<BusinessItem>()
        for (business in businesses) {
            if (GlobalMethods.getInstance().checkEqual(business.parent, parent.title)) {
                res.add(business)
            }
        }
        return res
    }

    fun tryBack(current: BusinessItem, layer: List<BusinessItem>? = null): List<BusinessItem>? {
        if (!BusinessApi::businesses.isInitialized) {
            return null
        }
        var currentLayer = layer
        if (currentLayer == null || currentLayer.isEmpty()) {
            currentLayer = businesses
        }
        for (business in currentLayer) {
            val children = getChildren(business)
            if (children == null || children.isEmpty()) {
                continue
            }
            for (child in children) {
                if (GlobalMethods.getInstance().checkEqual(child.title, current.title)) {
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

    private fun getClass(canonicalName: String): Class<*>? {
        return try {
            Class.forName(canonicalName)
        } catch (throwable: Throwable) {
            null
        }
    }
}