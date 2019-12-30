package com.example.myjetpackapplication.business.navigation.one

import android.view.View
import androidx.navigation.Navigation
import com.example.myjetpackapplication.business.navigation.R
import com.example.myjetpackapplication.business.navigation.databinding.FragmentOneBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class OneViewModel(host: OneFragment, binding: FragmentOneBinding) : BasicHostViewModel<OneViewModel, OneFragment, FragmentOneBinding>(host, binding) {
    fun onClickOne(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_oneFragment_to_twoFragment)
    }
}