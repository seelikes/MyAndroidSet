package ${escapeKotlinIdentifiers(packageName)}

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
<#if applicationPackage??>
import ${applicationPackage}.R
import ${applicationPackage}.databinding.${underscoreToCamelCase(activityLayout)}Binding
<#else>
import ${packageName}.R
import ${packageName}.databinding.${underscoreToCamelCase(activityLayout)}Binding
</#if>
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "${activityRoutePath}", title = "${activityTitleName}")
class ${activityClass} : BasicActivity<${activityClass}, ${viewModelClass}, ${underscoreToCamelCase(activityLayout)}Binding>() {
    override fun initModel(savedInstanceState: Bundle?): ${viewModelClass} = ${viewModelClass}(this, DataBindingUtil.setContentView(this, R.layout.${activityLayout}))
}