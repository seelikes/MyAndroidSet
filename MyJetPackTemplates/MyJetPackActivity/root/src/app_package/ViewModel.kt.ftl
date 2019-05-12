package ${escapeKotlinIdentifiers(packageName)}

import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.${underscoreToCamelCase(activityLayout)}Binding

class ${viewModelClass}(host: ${activityClass}, binding: ${underscoreToCamelCase(activityLayout)}Binding) : BasicHostViewModel<${viewModelClass}, ${activityClass}, ${underscoreToCamelCase(activityLayout)}Binding>(host, binding) {

}