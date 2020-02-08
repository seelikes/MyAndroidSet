package com.github.seelikes.android.duration.api

import android.util.Log
import java.util.concurrent.ConcurrentSkipListSet

/**
 * Created by liutiantian on 2020-02-05 11:08 星期三
 */
object DurationApi {
    private val durations: ConcurrentSkipListSet<Duration> = ConcurrentSkipListSet(Comparator { t1, t2 ->
        if (t1.hostClass == t2.hostClass) {
            if (t1.method == t2.method) {
                if (t1.startTime == t2.startTime) {
                    t1.endTime.compareTo(t2.endTime)
                }
                else {
                    t1.startTime.compareTo(t2.endTime)
                }
            }
            else {
                t1.method.compareTo(t2.method)
            }
        }
        else {
            t1.hostClass.compareTo(t2.hostClass)
        }
    })

    fun report(hostClass: String, method: String, startTime: Long, endTime: Long) {
        Log.i("DurationApi", "hostClass: $hostClass; method: $method; startTime: $startTime; endTime: $endTime")
        durations.add(Duration(hostClass, method, startTime, endTime))
    }

    fun group(durations: List<Duration>): List<DurationReport>? {
        Log.i("DurationApi", "group, durations: $durations")
        if (durations.isNotEmpty()) {
            val res = mutableListOf<DurationReport>()
            var report = DurationReport()
            for (duration in durations) {
                if (duration.hostClass != report.hostClass || duration.method != report.method) {
                    if (report.hostClass.isNotEmpty() && report.method.isNotEmpty()) {
                        res.add(report)
                    }
                    report = DurationReport(hostClass = duration.hostClass, method = duration.method, longestDuration = duration.duration, shortestDuration = duration.duration, averageDuration = duration.duration)
                }
                ++report.count
                report.totalDuration += duration.duration
                report.longestDuration = if (duration.duration > report.longestDuration) duration.duration else report.longestDuration
                report.shortestDuration = if (report.shortestDuration == -1L || duration.duration <= report.shortestDuration) duration.duration else report.shortestDuration
                report.averageDuration = report.totalDuration / report.count
            }
            res.add(report)
            return res
        }
        return null
    }

    fun snapshot(): List<Duration> {
        return durations.toList()
    }

    fun reset() {
        durations.clear()
    }
}