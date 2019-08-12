package ${escapeKotlinIdentifiers(packageName)}

<#if (fragmentLayoutRootComponent == "1" || fragmentLayoutRootComponent == "2")>
import androidx.databinding.ObservableInt
<#if applicationPackage??>
import ${applicationPackage}.R
<#else>
import ${packageName}.R
</#if>
</#if>
<#if applicationPackage??>
import ${applicationPackage}.databinding.${underscoreToCamelCase(fragmentLayout)}Binding
<#else>
import ${packageName}.databinding.${underscoreToCamelCase(fragmentLayout)}Binding
</#if>
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class ${viewModelClass}(host: ${fragmentClass}, binding: ${underscoreToCamelCase(fragmentLayout)}Binding) : BasicHostViewModel<${viewModelClass}, ${fragmentClass}, ${underscoreToCamelCase(fragmentLayout)}Binding>(host, binding) {
<#if fragmentLayoutRootComponent == "1" || fragmentLayoutRootComponent == "2">
    val title = ObservableInt(R.string.${fragmentTitleName})
</#if>
}