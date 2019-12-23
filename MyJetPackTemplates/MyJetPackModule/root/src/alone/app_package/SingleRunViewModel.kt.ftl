package ${packageName}

import androidx.databinding.ObservableInt
import ${packageName}.R
import ${packageName}.databinding.ActivitySingle${moduleName?cap_first}Binding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class Single${moduleName?cap_first}ViewModel(host: Single${moduleName?cap_first}Activity, binding: ActivitySingle${moduleName?cap_first}Binding) : BasicHostViewModel<Single${moduleName?cap_first}ViewModel, Single${moduleName?cap_first}Activity, ActivitySingle${moduleName?cap_first}Binding>(host, binding) {
    val title = ObservableInt(R.string.single_${classToResource(fakeClass?cap_first)}_title)
}