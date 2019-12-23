package ${escapeKotlinIdentifiers(packageName)}

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.Business
<#if applicationPackage??>
import ${applicationPackage}.R
import ${applicationPackage}.databinding.${underscoreToCamelCase(activityLayout)}Binding
<#else>
import ${packageName}.R
import ${packageName}.databinding.${underscoreToCamelCase(activityLayout)}Binding
</#if>
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(title = "${activityTitleName}")
@Route(path = "${activityRoutePath}")
class ${activityClass} : BasicActivity<${activityClass}, ${viewModelClass}, ${underscoreToCamelCase(activityLayout)}Binding>() {
    override fun initModel(savedInstanceState: Bundle?): ${viewModelClass} = ${viewModelClass}(this, DataBindingUtil.setContentView(this, R.layout.${activityLayout}))
}