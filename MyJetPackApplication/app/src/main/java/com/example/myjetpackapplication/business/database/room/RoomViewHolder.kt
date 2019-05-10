package com.example.myjetpackapplication.business.database.room

import android.content.Context
import androidx.databinding.ObservableField
import com.example.myjetpackapplication.basic.BasicViewHolder
import com.example.myjetpackapplication.business.database.room.data.RoomEntity
import com.example.myjetpackapplication.business.database.room.event.DeleteEvent
import com.example.myjetpackapplication.databinding.ItemRoomBinding
import com.example.myjetpackapplication.utils.format
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * Created by liutiantian on 2019-05-09 13:41 星期四
 */
class RoomViewHolder(context: Context, binding: ItemRoomBinding) : BasicViewHolder<RoomEntity, ItemRoomBinding>(context, binding) {
    val id = ObservableField<String>()
    val dice = ObservableField<String>()
    val time = ObservableField<String>()

    override fun setData(entity: RoomEntity?) {
        super.setData(entity)
        id.set(entity?.id?.toString(10) ?: "")
        dice.set(entity?.dice?.toString(10) ?: "")
        time.set(entity?.time?.let { format(Date(it)) })
    }

    fun onUiClickDelete() {
        EventBus.getDefault().post(DeleteEvent(entity))
    }
}