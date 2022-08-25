package com.xpense.android.presentation.xperiments.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.xpense.android.R
import com.xpense.android.databinding.FragmentPropertyAnimationBinding

class PropertyAnimationFragment : Fragment() {

    private lateinit var _star: ImageView
    private lateinit var _rotateButton: Button
    private lateinit var _translateButton: Button
    private lateinit var _scaleButton: Button
    private lateinit var _fadeButton: Button
    private lateinit var _colorizeButton: Button
    private lateinit var _showerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPropertyAnimationBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        _star = binding.star
        _rotateButton = binding.rotateButton
        _translateButton = binding.translateButton
        _scaleButton = binding.scaleButton
        _fadeButton = binding.fadeButton
        _colorizeButton = binding.colorizeButton
        _showerButton = binding.showerButton

        binding.rotateButton.setOnClickListener { rotater() }
        binding.translateButton.setOnClickListener { translater() }
        binding.scaleButton.setOnClickListener { scaler() }
        binding.fadeButton.setOnClickListener { fader() }
        binding.colorizeButton.setOnClickListener { colorizer() }
        binding.showerButton.setOnClickListener { shower() }

        return binding.root
    }


    private fun rotater() {

        // Rotate the view for a second around its center once

        val animator = ObjectAnimator.ofFloat(_star, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.disableViewDuringAnimation(_rotateButton)
        animator.start()
    }

    private fun translater() {

        // Translate the view 200 pixels to the right and back

        val animator = ObjectAnimator.ofFloat(_star, View.TRANSLATION_X, 200f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(_translateButton)
        animator.start()
    }

    private fun scaler() {

        // Scale the view up to 4x its default size and back

        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(_star, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(_scaleButton)
        animator.start()
    }

    private fun fader() {

        // Fade the view out to completely transparent and then back to completely opaque

        val animator = ObjectAnimator.ofFloat(_star, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(_fadeButton)
        animator.start()
    }

    private fun colorizer() {

        // Animate the color of the star's container from black to red over a half
        // second, then reverse back to black. Note that using a propertyName of
        // "backgroundColor" will cause the animator to call the backgroundColor property
        // (in Kotlin) or setBackgroundColor(int) (in Java).

        // Fixes lint error
        @SuppressLint("ObjectAnimatorBinding")
        val animator = ObjectAnimator.ofArgb(
            _star.parent,
            "backgroundColor",
            Color.BLACK,
            Color.RED
        )
        animator.duration = 500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(_colorizeButton)
        animator.start()
    }

    private fun shower() {

        // Create a new star view in a random X position above the container.
        // Make it rotateButton about its center as it falls to the bottom.

        // Local variables we'll need in the code below
        val container = _star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = _star.width.toFloat()
        var starH: Float = _star.height.toFloat()

        // Create the new star (an ImageView holding our drawable) and add it to the container
        val newStar = AppCompatImageView(requireContext())
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newStar)

        // Scale the view randomly between 10-160% of its default size
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        // Position the view at a random place between the left and right edges of the container
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        // Create an animator that moves the view from a starting position right about the container
        // to an ending position right below the container. Set an accelerate interpolator to give
        // it a gravity/falling feel
        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)

        // Create an animator to rotateButton the view around its center up to three times
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION,
            (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        // Use an AnimatorSet to play the falling and rotating animators in parallel for a duration
        // of a half-second to two seconds
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()

        // When the animation is done, remove the created view from the container
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                container.removeView(newStar)
            }
        })

        // Start the animation
        set.start()
    }

    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {

        // This extension method listens for start/end events on an animation and disables
        // the given view for the entirety of that animation.

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator) {
                view.isEnabled = true
            }
        })
    }
}
