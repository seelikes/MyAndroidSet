package com.example.myjetpackapplication.business.animation

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.animation.databinding.ActivityAnimationBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_animation.*

@Business(path = "/business/animation", title = "animation_title")
class AnimationActivity : BasicActivity<AnimationActivity, AnimationViewModel, ActivityAnimationBinding>() {
    override fun initModel(savedInstanceState: Bundle?): AnimationViewModel = AnimationViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_animation))

    fun onClickStartPropertyAnimation(view: View) {
        Logger.i("enter")
        val animator = AnimatorInflater.loadAnimator(this, R.animator.height_scale)
        if (animator.isRunning) {
            return
        }
        animator.setTarget(animation_first_view)
        animator.start()
    }

    fun onClickStartTweenAnimation(view: View) {
        Logger.i("enter")
        val anim = AnimationUtils.loadAnimation(this, R.anim.height_tween_scale)
        if (anim.hasStarted() && !anim.hasEnded()) {
            return
        }
        animation_first_view.startAnimation(anim)
    }
}