package com.example.myjetpackapplication.business.navigation.two

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.navigation.databinding.FragmentTwoBinding
import com.github.seelikes.android.mvvm.basic.BasicFragment

/**
 * Created by liutiantian on 2019-06-03 20:22 星期一
 */
class TwoFragment : BasicFragment<TwoFragment, TwoViewModel, FragmentTwoBinding>() { override fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): TwoViewModel = TwoViewModel(this, FragmentTwoBinding.inflate(inflater, container, false))

}