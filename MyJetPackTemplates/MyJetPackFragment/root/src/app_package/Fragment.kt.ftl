package ${escapeKotlinIdentifiers(packageName)}

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.basic.BasicFragment
import com.example.myjetpackapplication.databinding.${underscoreToCamelCase(fragmentLayout)}Binding

/**
 * Created by liutiantian on 2019-06-03 20:22 星期一
 */
class ${fragmentClass} : BasicFragment<${fragmentClass}, ${viewModelClass}, ${underscoreToCamelCase(fragmentLayout)}Binding>() {
    override fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ${viewModelClass} = ${viewModelClass}(this, ${underscoreToCamelCase(fragmentLayout)}Binding.inflate(inflater, container, false))
}