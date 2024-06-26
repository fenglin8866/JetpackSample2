/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xxh.learn.ui.layout.common.view

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import com.xxh.learn.ui.layout.common.view.SlidingTabLayout.TabColorizer
import kotlin.math.max
import kotlin.math.min

internal class SlidingTabStrip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val mBottomBorderThickness: Int
    private val mBottomBorderPaint: Paint

    private val mSelectedIndicatorThickness: Int
    private val mSelectedIndicatorPaint: Paint

    private val mDefaultBottomBorderColor: Int

    private val mDividerPaint: Paint
    private val mDividerHeight: Float

    private var mSelectedPosition = 0
    private var mSelectionOffset = 0f

    private var mCustomTabColorizer: TabColorizer? = null
    private val mDefaultTabColorizer: SimpleTabColorizer

    init {
        setWillNotDraw(false)

        val density = resources.displayMetrics.density

        val outValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorForeground, outValue, true)
        val themeForegroundColor = outValue.data

        mDefaultBottomBorderColor = setColorAlpha(
            themeForegroundColor,
            DEFAULT_BOTTOM_BORDER_COLOR_ALPHA
        )

        mDefaultTabColorizer = SimpleTabColorizer()
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR)
        mDefaultTabColorizer.setDividerColors(
            setColorAlpha(
                themeForegroundColor,
                DEFAULT_DIVIDER_COLOR_ALPHA
            )
        )

        mBottomBorderThickness = (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density).toInt()
        mBottomBorderPaint = Paint()
        mBottomBorderPaint.color = mDefaultBottomBorderColor

        mSelectedIndicatorThickness = (SELECTED_INDICATOR_THICKNESS_DIPS * density).toInt()
        mSelectedIndicatorPaint = Paint()

        mDividerHeight = DEFAULT_DIVIDER_HEIGHT
        mDividerPaint = Paint()
        mDividerPaint.strokeWidth =
            (DEFAULT_DIVIDER_THICKNESS_DIPS * density).toInt().toFloat()
    }

    fun setCustomTabColorizer(customTabColorizer: TabColorizer?) {
        mCustomTabColorizer = customTabColorizer
        invalidate()
    }

    fun setSelectedIndicatorColors(vararg colors: Int) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null
        mDefaultTabColorizer.setIndicatorColors(*colors)
        invalidate()
    }

    fun setDividerColors(vararg colors: Int) {
        // Make sure that the custom colorizer is removed
        mCustomTabColorizer = null
        mDefaultTabColorizer.setDividerColors(*colors)
        invalidate()
    }

    fun onViewPagerPageChanged(position: Int, positionOffset: Float) {
        mSelectedPosition = position
        mSelectionOffset = positionOffset
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val height = height
        val childCount = childCount
        val dividerHeightPx = (min(max(0.0, mDividerHeight.toDouble()), 1.0) * height).toInt()
        val tabColorizer = if (mCustomTabColorizer != null
        ) mCustomTabColorizer!!
        else mDefaultTabColorizer

        // Thick colored underline below the current selection
        if (childCount > 0) {
            val selectedTitle = getChildAt(mSelectedPosition)
            var left = selectedTitle.left
            var right = selectedTitle.right
            var color = tabColorizer.getIndicatorColor(mSelectedPosition)

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {
                val nextColor = tabColorizer.getIndicatorColor(mSelectedPosition + 1)
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset)
                }

                // Draw the selection partway between the tabs
                val nextTitle = getChildAt(mSelectedPosition + 1)
                left = (mSelectionOffset * nextTitle.left +
                        (1.0f - mSelectionOffset) * left).toInt()
                right = (mSelectionOffset * nextTitle.right +
                        (1.0f - mSelectionOffset) * right).toInt()
            }

            mSelectedIndicatorPaint.color = color

            canvas.drawRect(
                left.toFloat(), (height - mSelectedIndicatorThickness).toFloat(), right.toFloat(),
                height.toFloat(), mSelectedIndicatorPaint
            )
        }

        // Thin underline along the entire bottom edge
        canvas.drawRect(
            0f,
            (height - mBottomBorderThickness).toFloat(),
            width.toFloat(),
            height.toFloat(),
            mBottomBorderPaint
        )

        // Vertical separators between the titles
        val separatorTop = (height - dividerHeightPx) / 2
        for (i in 0 until childCount - 1) {
            val child = getChildAt(i)
            mDividerPaint.color = tabColorizer.getDividerColor(i)
            canvas.drawLine(
                child.right.toFloat(), separatorTop.toFloat(), child.right.toFloat(),
                (separatorTop + dividerHeightPx).toFloat(), mDividerPaint
            )
        }
    }

    private class SimpleTabColorizer : TabColorizer {
        private lateinit var mIndicatorColors: IntArray
        private lateinit var mDividerColors: IntArray

        override fun getIndicatorColor(position: Int): Int {
            return mIndicatorColors[position % mIndicatorColors.size]
        }

        override fun getDividerColor(position: Int): Int {
            return mDividerColors[position % mDividerColors.size]
        }

        fun setIndicatorColors(vararg colors: Int) {
            mIndicatorColors = colors
        }

        fun setDividerColors(vararg colors: Int) {
            mDividerColors = colors
        }
    }

    companion object {
        private const val DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2
        private const val DEFAULT_BOTTOM_BORDER_COLOR_ALPHA: Byte = 0x26
        private const val SELECTED_INDICATOR_THICKNESS_DIPS = 8
        private const val DEFAULT_SELECTED_INDICATOR_COLOR = -0xcc4a1b

        private const val DEFAULT_DIVIDER_THICKNESS_DIPS = 1
        private const val DEFAULT_DIVIDER_COLOR_ALPHA: Byte = 0x20
        private const val DEFAULT_DIVIDER_HEIGHT = 0.5f

        /**
         * Set the alpha value of the `color` to be the given `alpha` value.
         */
        private fun setColorAlpha(color: Int, alpha: Byte): Int {
            return Color.argb(
                alpha.toInt(),
                Color.red(color),
                Color.green(color),
                Color.blue(color)
            )
        }

        /**
         * Blend `color1` and `color2` using the given ratio.
         *
         * @param ratio of which to blend. 1.0 will return `color1`, 0.5 will give an even blend,
         * 0.0 will return `color2`.
         */
        private fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
            val inverseRation = 1f - ratio
            val r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation)
            val g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation)
            val b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation)
            return Color.rgb(r.toInt(), g.toInt(), b.toInt())
        }
    }
}