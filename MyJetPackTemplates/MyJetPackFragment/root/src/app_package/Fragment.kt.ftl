package ${escapeKotlinIdentifiers(packageName)}

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import ${applicationPackage}.databinding.${underscoreToCamelCase(fragmentLayout)}Binding
import com.github.seelikes.android.mvvm.basic.BasicFragment

/**
 * Created by liutiantian on 2019-06-03 20:22 星期一
 */
class ${fragmentClass} : BasicFragment<${fragmentClass}, ${viewModelClass}, ${underscoreToCamelCase(fragmentLayout)}Binding>() {
    override fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ${viewModelClass} = ${viewModelClass}(this, ${underscoreToCamelCase(fragmentLayout)}Binding.inflate(inflater, container, false))
}