package com.github.seelikes.android.duration.api

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.seelikes.java.algorithm.Sorts

/**
 * Created by liutiantian on 2020-02-06 13:12 星期四
 */
@Route(path = "/api/duration")
class DurationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duration)
    }

    override fun onResume() {
        super.onResume()
        val groups: MutableList<DurationReport> = DurationApi.group(DurationApi.snapshot())!!.toMutableList()
        Toast.makeText(this, "reports.size: ${groups.size}", Toast.LENGTH_LONG).show()
        var reports: MutableList<DurationReportGroup> = mutableListOf()
        Sorts.quickSort(groups) { t1, t2 ->
            (t2.averageDuration - t1.averageDuration).toInt()
        }
        reports.add(DurationReportGroup("Average Duration", reports = groups))
        Log.i("DurationActivity", "onResume, reports: $reports")
        val adapter = DurationAdapter(this, reports)
        val elList: ExpandableListView = findViewById(R.id.ev_list)
        elList.setAdapter(adapter)
        elList.setOnGroupCollapseListener {
            elList.parent.requestLayout()
        }
        elList.setOnGroupExpandListener {
            elList.parent.requestLayout()
        }
    }

    fun onClickBack(view: View) {
        finish()
    }
}