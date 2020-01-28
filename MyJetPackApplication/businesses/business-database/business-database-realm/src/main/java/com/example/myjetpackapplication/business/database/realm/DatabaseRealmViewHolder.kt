package com.example.myjetpackapplication.business.database.realm

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProvider
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmBean
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmEntity
import com.example.myjetpackapplication.business.database.realm.databinding.ItemDatabaseRealmBinding
import com.example.myjetpackapplication.business.database.realm.event.DeleteEvent
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import com.github.seelikes.android.mvvm.basic.context
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liutiantian on 2020-01-26 17:47 星期日
 */
class DatabaseRealmViewHolder(context: Context, binding: ItemDatabaseRealmBinding, routineScope: CoroutineScope?) : BasicViewHolder<DatabaseRealmBean, ItemDatabaseRealmBinding>(WeakReference(context), binding, routineScope) {
    val id = ObservableField<String>()
    val key = ObservableField<String>()
    val value = ObservableField<String>()
    val createTime = ObservableField<String>()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    override fun setData(entity: DatabaseRealmBean?) {
        super.setData(entity)
        Logger.i("202001271322, setData.entity: $entity")
        id.set(entity?.id?.toString())
        key.set(entity?.key)
        value.set(entity?.value)
        createTime.set(dateFormat.format(entity?.createTime))
    }

    fun onUiClickDelete() {
        entity?.let {
            EventBus.getDefault().post(DeleteEvent(it))
        }
    }
}