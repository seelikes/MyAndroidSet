package ${packageName}

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.BusinessItem
import ${packageName}.databinding.ItemSingle${moduleName?cap_first}Binding
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import com.orhanobut.logger.Logger

/**
 * Created by liutiantian on 2019-12-22 22:05 星期日
 */
class Single${moduleName?cap_first}ItemHolder(context: Context, binding: ItemSingle${moduleName?cap_first}Binding) : BasicViewHolder<BusinessItem, ItemSingle${moduleName?cap_first}Binding>(context, binding) {
    val title = ObservableInt()
    val hasChildren = ObservableBoolean()

    override fun setData(entity: BusinessItem?) {
        super.setData(entity)
        hasChildren.set(BusinessManager.getChildren(entity?.title).isNotEmpty())
        title.set(context?.resources?.getIdentifier(entity?.title, "string", context.packageName) ?: R.string.app_name)
    }

    fun onUiClick() {
        clickListener?.apply {
            invoke(entity)
        }
    }
}