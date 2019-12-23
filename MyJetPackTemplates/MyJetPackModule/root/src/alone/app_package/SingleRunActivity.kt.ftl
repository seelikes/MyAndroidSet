package ${packageName}

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.Business
import ${packageName}.R
import ${packageName}.databinding.ActivitySingle${moduleName?cap_first}Binding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Route(path = "/business/single/${moduleName?replace("-", "/")}")
class Single${moduleName?cap_first}Activity : BasicActivity<Single${moduleName?cap_first}Activity, Single${moduleName?cap_first}ViewModel, ActivitySingle${moduleName?cap_first}Binding>() {
    override fun initModel(savedInstanceState: Bundle?): Single${moduleName?cap_first}ViewModel = Single${moduleName?cap_first}ViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_single_${moduleName}))

    override fun initView(binding: ActivitySingle${moduleName?cap_first}Binding) {
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
        if (adapter !is Single${moduleName?cap_first}ItemAdapter) {
            adapter = Single${moduleName?cap_first}ItemAdapter(this, BusinessManager.getChildren(null)) { item, _ ->
                item?.let {
                    val children = BusinessManager.getChildren(it.title)
                    if (children.isNotEmpty()) {
                        ViewModelProviders.of(this).get(Single${moduleName?cap_first}DataModel::class.java).items.value = children
                    }
                    else {
                        ARouter.getInstance().build(it.path).navigation()
                    }
                }
            }
            binding.rvList.adapter = adapter
        }
    }
}