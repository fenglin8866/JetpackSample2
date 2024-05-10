package com.xxh.learn.ui.layout.recyclerview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.collection.ArrayMap
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xxh.common.BaseFragment
import com.xxh.learn.ui.R
import com.xxh.learn.ui.databinding.FragmentRecyclerView3Binding

class RecyclerViewFragment3 : BaseFragment<FragmentRecyclerView3Binding>() {


    lateinit var mRecyclerView: RecyclerView
    lateinit var mRadioGroup: RadioGroup
    lateinit var mPredictiveCB: CheckBox
    lateinit var mCustomCB: CheckBox
    var mDefaultItemAnimator: DefaultItemAnimator = DefaultItemAnimator()
    var mChangeAnimator: MyChangeAnimator = MyChangeAnimator()

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecyclerView3Binding {
        return FragmentRecyclerView3Binding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRadioGroup = mBinding.radioGroup
        mPredictiveCB = mBinding.predictiveCB
        mCustomCB = mBinding.customCB

        mRecyclerView = mBinding.recyclerview
        mRecyclerView.layoutManager = MyLinearLayoutManager(requireContext(),mPredictiveCB)
        mRecyclerView.adapter = RVAdapter()

        mCustomCB!!.setOnCheckedChangeListener { buttonView, isChecked ->
            mRecyclerView.itemAnimator = if (isChecked) mChangeAnimator else mDefaultItemAnimator
        }
    }


    /**
     * Custom adapter that supplies view holders to the RecyclerView. Our view holders
     * contain a simple LinearLayout (with a background color) and a TextView (displaying
     * the value of the container's bg color).
     */
    inner class RVAdapter : RecyclerView.Adapter<MyViewHolder>() {
        var mColors: ArrayList<Int> = ArrayList()

        override fun getItemCount(): Int {
            return mColors.size
        }

        private fun deleteItem(view: View) {
            val position: Int = mRecyclerView.getChildAdapterPosition(view)
            if (position != RecyclerView.NO_POSITION) {
                mColors.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        private fun addItem(view: View) {
            val position: Int = mRecyclerView.getChildAdapterPosition(view)
            if (position != RecyclerView.NO_POSITION) {
                val color = generateColor()
                mColors.add(position, color)
                notifyItemInserted(position)
            }
        }

        private fun changeItem(view: View) {
            val position: Int = mRecyclerView.getChildAdapterPosition(view)
            if (position != RecyclerView.NO_POSITION) {
                val color = generateColor()
                mColors[position] = color
                notifyItemChanged(position)
            }
        }

        private val mItemAction =
            View.OnClickListener { v ->
                when (mRadioGroup.getCheckedRadioButtonId()) {
                    R.id.deleteRB -> deleteItem(v)
                    R.id.addRB -> addItem(v)
                    R.id.changeRB -> changeItem(v)
                }
            }

        init {
            generateData()
        }

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
           val container: View = getLayoutInflater().inflate(R.layout.item_layout3, parent, false)
           container.setOnClickListener(mItemAction)
           return MyViewHolder(container)
       }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val myHolder = holder as MyViewHolder
            val color = mColors[position]
            myHolder.container.setBackgroundColor(color)
            myHolder.textView.text = "#" + Integer.toHexString(color)
        }

        private fun generateColor(): Int {
            val red = ((Math.random() * 200).toInt())
            val green = ((Math.random() * 200).toInt())
            val blue = ((Math.random() * 200).toInt())
            return Color.rgb(red, green, blue)
        }

        private fun generateData() {
            for (i in 0..99) {
                mColors.add(generateColor())
            }
        }
    }

    /**
     * Simple extension of LinearLayoutManager for the sole purpose of showing what happens
     * when predictive animations (which are enabled by default in LinearLayoutManager) are
     * not enabled. This behavior is toggled via a checkbox in the UI.
     */
    private class MyLinearLayoutManager(context: Context?,val mPredictiveCB:CheckBox) : LinearLayoutManager(context) {
        override fun supportsPredictiveItemAnimations(): Boolean {
            return mPredictiveCB.isChecked()
        }
    }

    /**
     * Custom ItemAnimator that handles change animations. The default animator simple cross-
     * fades the old and new ViewHolder items. Our custom animation re-uses the same
     * ViewHolder and animates the contents of the views that it contains. The animation
     * consists of a color fade through black and a text rotation. The animation also handles
     * interruption, when a new change event happens on an item that is currently being
     * animated.
     */
    class MyChangeAnimator : DefaultItemAnimator() {
        // stateless interpolators re-used for every change animation
        private val mAccelerateInterpolator = AccelerateInterpolator()
        private val mDecelerateInterpolator = DecelerateInterpolator()
        var mColorEvaluator: ArgbEvaluator = ArgbEvaluator()

        // Maps to hold running animators. These are used when running a new change
        // animation on an item that is already being animated. mRunningAnimators is
        // used to cancel the previous animation. mAnimatorMap is used to construct
        // the new change animation based on where the previous one was at when it
        // was interrupted.
        private val mAnimatorMap = ArrayMap<RecyclerView.ViewHolder, AnimatorInfo>()

        override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
            // This allows our custom change animation on the contents of the holder instead
            // of the default behavior of replacing the viewHolder entirely
            return true
        }

        private fun getItemHolderInfo(
            viewHolder: MyViewHolder,
            info: ColorTextInfo
        ): ItemHolderInfo {
            val myHolder = viewHolder
            val bgColor = (myHolder.container.background as ColorDrawable).color
            info.color = bgColor
            info.text = myHolder.textView.text as String
            return info
        }

        override fun recordPreLayoutInformation(
            state: RecyclerView.State,
            viewHolder: RecyclerView.ViewHolder, changeFlags: Int, payloads: List<Any>
        ): ItemHolderInfo {
            val info = super.recordPreLayoutInformation(
                state, viewHolder,
                changeFlags, payloads
            ) as ColorTextInfo
            return getItemHolderInfo(viewHolder as MyViewHolder, info)
        }

        override fun recordPostLayoutInformation(
            state: RecyclerView.State,
            viewHolder: RecyclerView.ViewHolder
        ): ItemHolderInfo {
            val info = super.recordPostLayoutInformation(state, viewHolder) as ColorTextInfo
            return getItemHolderInfo(viewHolder as MyViewHolder, info)
        }

        override fun obtainHolderInfo(): ItemHolderInfo {
            return ColorTextInfo()
        }

        /**
         * Custom ItemHolderInfo class that holds color and text information used in
         * our custom change animation
         */
        private inner class ColorTextInfo : ItemHolderInfo() {
            var color: Int = 0
            var text: String? = null
        }

        /**
         * Holds child animator objects for any change animation. Used when a new change
         * animation interrupts one already in progress; the new one is constructed to start
         * from where the previous one was at when the interruption occurred.
         */
        private inner class AnimatorInfo(
            var overallAnim: Animator,
            var fadeToBlackAnim: ObjectAnimator?,
            var fadeFromBlackAnim: ObjectAnimator,
            var oldTextRotator: ObjectAnimator?,
            var newTextRotator: ObjectAnimator
        )


        /**
         * Custom change animation. Fade to black on the container background, then back
         * up to the new bg coolor. Meanwhile, the text rotates, switching along the way.
         * If a new change animation occurs on an item that is currently animating
         * a change, we stop the previous change and start the new one where the old
         * one left off.
         */
        override fun animateChange(
            oldHolder: RecyclerView.ViewHolder,
            newHolder: RecyclerView.ViewHolder,
            preInfo: ItemHolderInfo, postInfo: ItemHolderInfo
        ): Boolean {
            if (oldHolder !== newHolder) {
                // use default behavior if not re-using view holders
                return super.animateChange(oldHolder, newHolder, preInfo, postInfo)
            }

            val viewHolder = newHolder as MyViewHolder

            // Get the pre/post change values; these are what we are animating between
            val oldInfo = preInfo as ColorTextInfo
            val newInfo = postInfo as ColorTextInfo
            val oldColor = oldInfo.color
            val newColor = newInfo.color
            val oldText = oldInfo.text
            val newText = newInfo.text

            // These are the objects whose values will be animated
            val newContainer = viewHolder.container
            val newTextView = viewHolder.textView

            // Check to see if there's a change animation already running on this item
            val runningInfo = mAnimatorMap[newHolder]
            var prevAnimPlayTime: Long = 0
            var firstHalf = false
            if (runningInfo != null) {
                // The information we need to construct the new animators is whether we
                // are in the 'first half' (fading to black and rotating the old text out)
                // and how far we are in whichever half is running
                firstHalf = runningInfo.oldTextRotator != null &&
                        runningInfo.oldTextRotator!!.isRunning
                prevAnimPlayTime =
                    if (firstHalf) runningInfo.oldTextRotator!!.currentPlayTime else runningInfo.newTextRotator.currentPlayTime
                // done with previous animation - cancel it
                runningInfo.overallAnim.cancel()
            }

            // Construct the fade to/from black animation
            var fadeToBlack: ObjectAnimator? = null
            if (runningInfo == null || firstHalf) {
                // The first part of the animation fades to black. Skip this phase
                // if we're interrupting an animation that was already in the second phase.
                var startColor = oldColor
                if (runningInfo != null) {
                    startColor = runningInfo.fadeToBlackAnim?.animatedValue as Int
                }
                fadeToBlack = ObjectAnimator.ofInt(
                    newContainer, "backgroundColor",
                    startColor, Color.BLACK
                )
                fadeToBlack.setEvaluator(mColorEvaluator)
                if (runningInfo != null) {
                    // Seek to appropriate time in new animator if we were already
                    // running a previous animation
                    fadeToBlack.currentPlayTime = prevAnimPlayTime
                }
            }

            // Second phase of animation fades from black to the new bg color
            val fadeFromBlack = ObjectAnimator.ofInt(
                newContainer, "backgroundColor",
                Color.BLACK, newColor
            )
            fadeFromBlack.setEvaluator(mColorEvaluator)
            if (runningInfo != null && !firstHalf) {
                // Seek to appropriate time in new animator if we were already
                // running a previous animation
                fadeFromBlack.currentPlayTime = prevAnimPlayTime
            }

            // Set up an animation to play both the first (if non-null) and second phases
            val bgAnim = AnimatorSet()
            if (fadeToBlack != null) {
                bgAnim.playSequentially(fadeToBlack, fadeFromBlack)
            } else {
                bgAnim.play(fadeFromBlack)
            }

            // The other part of the animation rotates the text, switching it to the
            // new value half-way through (when it is perpendicular to the user)
            var oldTextRotate: ObjectAnimator? = null
            if (runningInfo == null || firstHalf) {
                // The first part of the animation rotates text to be perpendicular to user.
                // Skip this phase if we're interrupting an animation that was already
                // in the second phase.
                oldTextRotate = ObjectAnimator.ofFloat(newTextView, View.ROTATION_X, 0f, 90f)
                oldTextRotate.interpolator = mAccelerateInterpolator
                if (runningInfo != null) {
                    oldTextRotate.currentPlayTime = prevAnimPlayTime
                }
                oldTextRotate.addListener(object : AnimatorListenerAdapter() {
                    var mCanceled: Boolean = false
                    override fun onAnimationStart(animation: Animator) {
                        // text was changed as part of the item change notification. Change
                        // it back for the first phase of the animation
                        newTextView.text = oldText
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        mCanceled = true
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        if (!mCanceled) {
                            // Set it to the new text when the old rotator ends - this is when
                            // it is perpendicular to the user (thus making the switch
                            // invisible)
                            newTextView.text = newText
                        }
                    }
                })
            }

            // Second half of text rotation rotates from perpendicular to 0
            val newTextRotate = ObjectAnimator.ofFloat(newTextView, View.ROTATION_X, -90f, 0f)
            newTextRotate.interpolator = mDecelerateInterpolator
            if (runningInfo != null && !firstHalf) {
                // If we're interrupting a previous second-phase animation, seek to that time
                newTextRotate.currentPlayTime = prevAnimPlayTime
            }

            // Choreograph first and second half. First half may be null if we interrupted
            // a second-phase animation
            val textAnim = AnimatorSet()
            if (oldTextRotate != null) {
                textAnim.playSequentially(oldTextRotate, newTextRotate)
            } else {
                textAnim.play(newTextRotate)
            }

            // Choreograph both animations: color fading and text rotating
            val changeAnim = AnimatorSet()
            changeAnim.playTogether(bgAnim, textAnim)
            changeAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    dispatchAnimationFinished(newHolder)
                    mAnimatorMap.remove(newHolder)
                }
            })
            changeAnim.start()

            // Store info about this animation to be re-used if a succeeding change event
            // occurs while it's still running
            val runningAnimInfo: AnimatorInfo = AnimatorInfo(
                changeAnim, fadeToBlack, fadeFromBlack,
                oldTextRotate, newTextRotate
            )
            mAnimatorMap[newHolder] = runningAnimInfo

            return true
        }

        override fun endAnimation(item: RecyclerView.ViewHolder) {
            super.endAnimation(item)
            if (!mAnimatorMap.isEmpty) {
                val numRunning: Int = mAnimatorMap.size
                for (i in numRunning downTo 0) {
                    if (item === mAnimatorMap.keyAt(i)) {
                        mAnimatorMap.valueAt(i).overallAnim.cancel()
                    }
                }
            }
        }

        override fun isRunning(): Boolean {
            return super.isRunning() || !mAnimatorMap.isEmpty
        }

        override fun endAnimations() {
            super.endAnimations()
            if (!mAnimatorMap.isEmpty) {
                val numRunning: Int = mAnimatorMap.size
                for (i in numRunning downTo 0) {
                    mAnimatorMap.valueAt(i).overallAnim.cancel()
                }
            }
        }
    }


     class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var textView: TextView = v.findViewById<View>(R.id.textview) as TextView
        var container: LinearLayout = v as LinearLayout

        override fun toString(): String {
            return super.toString() + " \"" + textView.text + "\""
        }
    }

}