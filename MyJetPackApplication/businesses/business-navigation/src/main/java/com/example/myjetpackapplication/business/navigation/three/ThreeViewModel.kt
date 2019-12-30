package com.example.myjetpackapplication.business.navigation.three

import android.view.View
import androidx.navigation.Navigation
import com.example.myjetpackapplication.business.navigation.R
import com.example.myjetpackapplication.business.navigation.databinding.FragmentThreeBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class ThreeViewModel(host: ThreeFragment, binding: FragmentThreeBinding) : BasicHostViewModel<ThreeViewModel, ThreeFragment, FragmentThreeBinding>(host, binding) {
    fun onClickThree(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_threeFragment_to_oneFragment)
    }
}