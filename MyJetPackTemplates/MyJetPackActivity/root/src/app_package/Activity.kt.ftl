package ${escapeKotlinIdentifiers(packageName)}

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicActivity
import com.example.myjetpackapplication.databinding.${underscoreToCamelCase(activityLayout)}Binding

@Route(path = "${activityRoutePath}")
class ${activityClass} : BasicActivity<${activityClass}, ${viewModelClass}, ${underscoreToCamelCase(activityLayout)}Binding>() {
    override fun initModel(savedInstanceState: Bundle?): ${viewModelClass} = ${viewModelClass}(this, DataBindingUtil.setContentView(this, R.layout.${activityLayout}))
}