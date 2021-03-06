package ${escapeKotlinIdentifiers(packageName)}

import androidx.databinding.ObservableInt
<#if applicationPackage??>
import ${applicationPackage}.R
import ${applicationPackage}.databinding.${underscoreToCamelCase(activityLayout)}Binding
<#else>
import ${packageName}.R
import ${packageName}.databinding.${underscoreToCamelCase(activityLayout)}Binding
</#if>
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class ${viewModelClass}(host: ${activityClass}, binding: ${underscoreToCamelCase(activityLayout)}Binding) : BasicHostViewModel<${viewModelClass}, ${activityClass}, ${underscoreToCamelCase(activityLayout)}Binding>(host, binding) {
    val title = ObservableInt(R.string.${activityTitleName})
}