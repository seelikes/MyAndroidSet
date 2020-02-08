package com.github.seelikes.android.duration.api

import android.content.Context
import android.util.ArraySet
import android.util.AttributeSet
import android.util.SparseArray
import android.widget.ExpandableListView

/**
 * Created by liutiantian on 2020-02-08 19:08 星期六
 */
class DurationExpandableListView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ExpandableListView(context, attrs, defStyleAttr) {
    private var expandedSet: MutableSet<Int> = mutableSetOf()

    init {
        setOnGroupExpandListener {
            expandedSet.add(it)
            invalidate()
        }
        setOnGroupCollapseListener {
            expandedSet.remove(it)
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (expandedSet.isNotEmpty()) {
            super.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            layoutParams.height = measuredHeight
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}