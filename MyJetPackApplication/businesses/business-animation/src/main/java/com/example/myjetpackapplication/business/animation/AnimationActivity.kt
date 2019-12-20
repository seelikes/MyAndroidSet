package com.example.myjetpackapplication.business.animation

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.business.animation.databinding.ActivityAnimationBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_animation.*

// TODO 修改ARouter
@Route(path = "/business12/animation")
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
}