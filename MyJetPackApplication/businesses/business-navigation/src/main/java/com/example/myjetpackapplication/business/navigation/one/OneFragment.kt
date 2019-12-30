package com.example.myjetpackapplication.business.navigation.one

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.navigation.databinding.FragmentOneBinding
import com.github.seelikes.android.mvvm.basic.BasicFragment

/**
 * Created by liutiantian on 2019-06-03 20:22 星期一
 */
class OneFragment : BasicFragment<OneFragment, OneViewModel, FragmentOneBinding>() {
    override fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): OneViewModel = OneViewModel(this, FragmentOneBinding.inflate(inflater, container, false))
}