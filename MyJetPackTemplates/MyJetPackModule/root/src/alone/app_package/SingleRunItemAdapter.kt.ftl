package ${packageName}

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.BusinessItem
import ${packageName}.databinding.ItemSingle${moduleName?cap_first}Binding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class Single${moduleName?cap_first}ItemAdapter(context: Context, items: List<BusinessItem>?, itemClickListener: (BusinessItem?, Int) -> Unit) : BasicRecyclerAdapter<BusinessItem, Single${moduleName?cap_first}ItemHolder, ItemSingle${moduleName?cap_first}Binding>(context, items, itemClickListener) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): Single${moduleName?cap_first}ItemHolder = Single${moduleName?cap_first}ItemHolder(context, ItemSingle${moduleName?cap_first}Binding.inflate(LayoutInflater.from(context)))
}