package ${packageName}

import androidx.databinding.ObservableInt
import ${packageName}.R
import ${packageName}.databinding.Activity${underscoreToCamelCase(activitySingleLayout)}Binding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class ${underscoreToCamelCase(activitySingleLayout)}ViewModel(host: ${underscoreToCamelCase(activitySingleLayout)}Activity, binding: Activity${underscoreToCamelCase(activitySingleLayout)}Binding) : BasicHostViewModel<${underscoreToCamelCase(activitySingleLayout)}ViewModel, ${underscoreToCamelCase(activitySingleLayout)}Activity, Activity${underscoreToCamelCase(activitySingleLayout)}Binding>(host, binding) {
    val title = ObservableInt(R.string.single_${moduleNameShiftPrefix?replace("-", "_")}_title)
}