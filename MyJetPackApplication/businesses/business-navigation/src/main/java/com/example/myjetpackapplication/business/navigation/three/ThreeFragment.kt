package com.example.myjetpackapplication.business.navigation.three

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.navigation.databinding.FragmentThreeBinding
import com.github.seelikes.android.mvvm.basic.BasicFragment

/**
 * Created by liutiantian on 2019-06-03 20:22 星期一
 */
class ThreeFragment : BasicFragment<ThreeFragment, ThreeViewModel, FragmentThreeBinding>() {
    override fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ThreeViewModel = ThreeViewModel(this, FragmentThreeBinding.inflate(inflater, container, false))
}