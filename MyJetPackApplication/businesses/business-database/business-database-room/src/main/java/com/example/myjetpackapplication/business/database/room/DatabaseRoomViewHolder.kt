package com.example.myjetpackapplication.business.database.room

import android.content.Context
import androidx.databinding.ObservableField
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import com.example.myjetpackapplication.business.database.room.data.DatabaseRoomEntity
import com.example.myjetpackapplication.business.database.room.databinding.ItemDatabaseRoomBinding
import com.example.myjetpackapplication.business.database.room.event.DeleteEvent
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liutiantian on 2019-05-09 13:41 星期四
 */
class DatabaseRoomViewHolder(context: Context, binding: ItemDatabaseRoomBinding) : BasicViewHolder<DatabaseRoomEntity, ItemDatabaseRoomBinding>(context, binding) {
    val id = ObservableField<String>()
    val dice = ObservableField<String>()
    val time = ObservableField<String>()

    override fun setData(entity: DatabaseRoomEntity?) {
        super.setData(entity)
        id.set(entity?.id?.toString(10) ?: "")
        dice.set(entity?.dice?.toString(10) ?: "")
        time.set(entity?.time?.let { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date(it)) })
    }

    fun onUiClickDelete() {
        EventBus.getDefault().post(DeleteEvent(entity))
    }
}