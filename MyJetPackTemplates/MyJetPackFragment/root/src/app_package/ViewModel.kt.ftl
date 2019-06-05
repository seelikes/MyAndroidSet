package ${escapeKotlinIdentifiers(packageName)}

<#if fragmentLayoutRootComponent == "1" || fragmentLayoutRootComponent == "2">
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.R
</#if>
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.${underscoreToCamelCase(fragmentLayout)}Binding

class ${viewModelClass}(host: ${fragmentClass}, binding: ${underscoreToCamelCase(fragmentLayout)}Binding) : BasicHostViewModel<${viewModelClass}, ${fragmentClass}, ${underscoreToCamelCase(fragmentLayout)}Binding>(host, binding) {
<#if fragmentLayoutRootComponent == "1" || fragmentLayoutRootComponent == "2">
    val title = ObservableInt(R.string.${fragmentTitleName})
</#if>
}