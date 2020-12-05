package ${packageName}

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi
import ${packageName}.databinding.Activity${underscoreToCamelCase(activitySingleLayout)}Binding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/single/${moduleNameShiftPrefix?replace("-", "/")}")
class ${underscoreToCamelCase(activitySingleLayout)}Activity : BasicActivity<${underscoreToCamelCase(activitySingleLayout)}Activity, ${underscoreToCamelCase(activitySingleLayout)}ViewModel, Activity${underscoreToCamelCase(activitySingleLayout)}Binding>() {
    override fun initModel(savedInstanceState: Bundle?): ${underscoreToCamelCase(activitySingleLayout)}ViewModel = ${underscoreToCamelCase(activitySingleLayout)}ViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_${activitySingleLayout}))

    override fun initView(binding: Activity${underscoreToCamelCase(activitySingleLayout)}Binding) {
        super.initView(binding)
        if (binding.rvList.layoutManager == null) {
            binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        if (binding.rvList.itemDecorationCount == 0) {
            binding.rvList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    if (parent.getChildAdapterPosition(view) != 0) {
                        outRect.top = 2
                    }
                }
            })
        }
        var adapter = binding.rvList.adapter
        if (adapter !is ${underscoreToCamelCase(activitySingleLayout)}ItemAdapter) {
            adapter = ${underscoreToCamelCase(activitySingleLayout)}ItemAdapter(this, BusinessApi.getChildren(null)) { item, _ ->
                item?.let {
                    val children = BusinessApi.getChildren(ViewModelProviders.of(this).get(${underscoreToCamelCase(activitySingleLayout)}DataModel::class.java).items.value?.get(0))
                    if (!children.isNullOrEmpty()) {
                        ViewModelProviders.of(this).get(${underscoreToCamelCase(activitySingleLayout)}DataModel::class.java).items.value = children
                    }
                    else {
                        item.path?.let { path ->
                            BusinessApi.go(this, path)
                        }
                    }
                }
            }
            binding.rvList.adapter = adapter
        }
    }

    override fun onBackPressed() {
        val pageUp = BusinessApi.tryBack(ViewModelProviders.of(this).get(${underscoreToCamelCase(activitySingleLayout)}DataModel::class.java).items.value?.get(0), null)
        if (pageUp?.isNotEmpty() == true) {
            ViewModelProviders.of(this).get(${underscoreToCamelCase(activitySingleLayout)}DataModel::class.java).items.value = pageUp
            return
        }
        super.onBackPressed()
    }
}