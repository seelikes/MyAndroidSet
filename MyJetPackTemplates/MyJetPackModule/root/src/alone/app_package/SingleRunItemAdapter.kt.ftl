package ${packageName}

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import ${packageName}.databinding.Item${underscoreToCamelCase(activitySingleLayout)}Binding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class ${underscoreToCamelCase(activitySingleLayout)}ItemAdapter(context: Context, items: List<BusinessItem>?, itemClickListener: (BusinessItem?, Int) -> Unit) : BasicRecyclerAdapter<BusinessItem, ${underscoreToCamelCase(activitySingleLayout)}ItemHolder, Item${underscoreToCamelCase(activitySingleLayout)}Binding>(context, items, itemClickListener) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): ${underscoreToCamelCase(activitySingleLayout)}ItemHolder = ${underscoreToCamelCase(activitySingleLayout)}ItemHolder(WeakReference(context), Item${underscoreToCamelCase(activitySingleLayout)}Binding.inflate(LayoutInflater.from(context)))
}