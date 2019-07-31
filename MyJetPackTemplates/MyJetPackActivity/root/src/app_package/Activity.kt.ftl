package ${escapeKotlinIdentifiers(packageName)}

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import ${applicationPackage}.R
import ${applicationPackage}.databinding.${underscoreToCamelCase(activityLayout)}Binding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Route(path = "${activityRoutePath}")
class ${activityClass} : BasicActivity<${activityClass}, ${viewModelClass}, ${underscoreToCamelCase(activityLayout)}Binding>() {
    override fun initModel(savedInstanceState: Bundle?): ${viewModelClass} = ${viewModelClass}(this, DataBindingUtil.setContentView(this, R.layout.${activityLayout}))
}