package com.example.myjetpackapplication.business.animation

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.animation.databinding.ActivityAnimationBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class AnimationViewModel(host: AnimationActivity, binding: ActivityAnimationBinding) : BasicHostViewModel<AnimationViewModel, AnimationActivity, ActivityAnimationBinding>(host, binding) {
    val title = ObservableInt(R.string.animation_title)
}