package com.github.seelikes.android.duration.api

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

/**
 * Created by liutiantian on 2020-02-07 18:46 星期五
 */
class DurationAdapter(private val context: Context, private val groups: List<DurationReportGroup>) : BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): Any = groups[groupPosition]

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = false

    override fun hasStableIds(): Boolean = true

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        var holder: GroupViewHolder? = view?.getTag(R.id.tag_duration_group) as? GroupViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_duration_group, parent, false)
            holder = GroupViewHolder()
            // TODO 为什么这里的view无法联想到 View#findViewById 1211123
            holder.title = view?.findViewById(R.id.title)
            holder.icon = view?.findViewById(R.id.icon)
        }
        val group = groups[groupPosition]
        holder?.title?.text = group.title
        return view!!
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        val size = groups[groupPosition].reports?.size ?: groups[groupPosition].durations?.size ?: 0
        Log.i("DurationAdapter", "getChildrenCount, size: $size")
        return size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any = (groups[groupPosition].reports ?: groups[groupPosition].durations)?.get(childPosition)!!

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        Log.i("DurationAdapter", "getChildView, convertView != null: ${convertView != null}")

//        return LayoutInflater.from(context).inflate(R.layout.item_duration, parent, false)

        val report: DurationReport? = groups[groupPosition].reports?.get(childPosition)
        Log.i("DurationAdapter", "getChildView, report != null: ${report != null}")
        if (report != null) {
            var holder: DurationReportViewHolder? = convertView?.getTag(R.id.tag_duration_report) as? DurationReportViewHolder
            if (holder == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_duration_report, parent, false)
                holder = DurationReportViewHolder()
                holder.hostClass = view?.findViewById(R.id.host_class)
                holder.method = view?.findViewById(R.id.method)
                holder.count = view?.findViewById(R.id.count)
                holder.totalDuration = view?.findViewById(R.id.total_duration)
                holder.longestDuration = view?.findViewById(R.id.longest_duration)
                holder.shortestDuration = view?.findViewById(R.id.shortest_duration)
                holder.averageDuration = view?.findViewById(R.id.average_duration)
                view?.setTag(R.id.tag_duration_report, holder)
            }
            holder.hostClass?.text = context.getString(R.string.host_class, report.hostClass)
            holder.method?.text = context.getString(R.string.method, report.method)
            holder.count?.text = context.getString(R.string.count, report.count)
            holder.totalDuration?.text = context.getString(R.string.total_duration, report.totalDuration)
            holder.longestDuration?.text = context.getString(R.string.longest_duration, report.longestDuration)
            holder.shortestDuration?.text = context.getString(R.string.shortest_duration, report.shortestDuration)
            holder.averageDuration?.text = context.getString(R.string.average_duration, report.averageDuration)
            return view!!
        }
        else {
            val duration: Duration? = groups[groupPosition].durations?.get(childPosition)
            if (duration != null) {
                var holder: DurationViewHolder? = convertView?.getTag(R.id.tag_duration) as? DurationViewHolder
                if (holder == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.item_duration, parent, false)
                    holder = DurationViewHolder()
                    holder.hostClass = view?.findViewById(R.id.host_class)
                    holder.method = view?.findViewById(R.id.method)
                    holder.duration = view?.findViewById(R.id.duration)
                    holder.startTime = view?.findViewById(R.id.start_time)
                    holder.endTime = view?.findViewById(R.id.end_time)
                }
                holder.hostClass?.text = context.getString(R.string.host_class, duration.hostClass)
                holder.method?.text = context.getString(R.string.method, duration.method)
                holder.duration?.text = context.getString(R.string.duration, duration.duration)
                holder.startTime?.text = context.getString(R.string.start_time, dateFormat.format(Date(duration.startTime)))
                holder.endTime?.text = context.getString(R.string.end_time, dateFormat.format(Date(duration.endTime)))
                return view!!
            }
            throw IllegalStateException("code should never comes here")
        }
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun getGroupCount(): Int = groups.size

    class GroupViewHolder {
        var title: TextView? = null
        var icon: ImageView? = null
    }

    class DurationViewHolder {
        var hostClass: TextView? = null
        var method: TextView? = null
        var duration: TextView? = null
        var startTime: TextView? = null
        var endTime: TextView? = null
    }

    class DurationReportViewHolder {
        var hostClass: TextView? = null
        var method: TextView? = null
        var count: TextView? = null
        var totalDuration: TextView? = null
        var longestDuration: TextView? = null
        var shortestDuration: TextView? = null
        var averageDuration: TextView? = null
    }
}