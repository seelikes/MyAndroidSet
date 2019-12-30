package com.example.myjetpackapplication.business.navigation.two

import android.view.View
import androidx.navigation.Navigation
import com.example.myjetpackapplication.business.navigation.R
import com.example.myjetpackapplication.business.navigation.databinding.FragmentTwoBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class TwoViewModel(host: TwoFragment, binding: FragmentTwoBinding) : BasicHostViewModel<TwoViewModel, TwoFragment, FragmentTwoBinding>(host, binding) {
    fun onClickTwo(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_twoFragment_to_threeFragment)
    }
}