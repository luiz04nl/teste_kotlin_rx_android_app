package com.teste.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout

internal class SlidingTabStrip
    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : LinearLayout(context, attrs) {

    private val mBottomBorderThickness: Int
    private val mBottomBorderPaint: Paint

    private val mSelectedIndicatorThickness: Int
    private val mSelectedIndicatorPaint: Paint

    private var mSelectedPosition: Int = 0
    private var mSelectionOffset: Float = 0.toFloat()

    private var mCustomTabColorizer: SlidingTabLayout.TabColorizer? = null
    private val mDefaultTabColorizer: SimpleTabColorizer

    init {
        setWillNotDraw(false)

        val density = resources.displayMetrics.density

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.colorForeground, outValue, true)
        val themeForegroundColor = outValue.data

        val defaultBottomBorderColor = setColorAlpha(themeForegroundColor,
                DEFAULT_BOTTOM_BORDER_COLOR_ALPHA)

        mDefaultTabColorizer = SimpleTabColorizer()
        mDefaultTabColorizer.setIndicatorColors(DEFAULT_SELECTED_INDICATOR_COLOR)

        mBottomBorderThickness = (DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS * density).toInt()
        mBottomBorderPaint = Paint()
        mBottomBorderPaint.color = defaultBottomBorderColor

        mSelectedIndicatorThickness = (SELECTED_INDICATOR_THICKNESS_DIPS * density).toInt()
        mSelectedIndicatorPaint = Paint()
    }

    fun setCustomTabColorizer(customTabColorizer: SlidingTabLayout.TabColorizer) {
        mCustomTabColorizer = customTabColorizer
        invalidate()
    }

    fun setSelectedIndicatorColors(vararg colors: Int) {
        mCustomTabColorizer = null
        mDefaultTabColorizer.setIndicatorColors(*colors)
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
        val tabColorizer = if (mCustomTabColorizer != null)
            mCustomTabColorizer
        else
            mDefaultTabColorizer

        if (childCount > 0) {
            val selectedTitle = getChildAt(mSelectedPosition)
            var left = selectedTitle.left
            var right = selectedTitle.right
            var color = tabColorizer!!.getIndicatorColor(mSelectedPosition)

            if (mSelectionOffset > 0f && mSelectedPosition < getChildCount() - 1) {
                val nextColor = tabColorizer!!.getIndicatorColor(mSelectedPosition + 1)
                if (color != nextColor) {
                    color = blendColors(nextColor, color, mSelectionOffset)
                }

                val nextTitle = getChildAt(mSelectedPosition + 1)
                left = (mSelectionOffset * nextTitle.left + (1.0f - mSelectionOffset) * left).toInt()
                right = (mSelectionOffset * nextTitle.right + (1.0f - mSelectionOffset) * right).toInt()
            }

            mSelectedIndicatorPaint.color = color

            canvas.drawRect(left.toFloat(), (height - mSelectedIndicatorThickness).toFloat(), right.toFloat(),
                    height.toFloat(), mSelectedIndicatorPaint)
        }

        canvas.drawRect(0f, (height - mBottomBorderThickness).toFloat(), width.toFloat(), height.toFloat(), mBottomBorderPaint)
    }

    private class SimpleTabColorizer : SlidingTabLayout.TabColorizer {
        private var mIndicatorColors: IntArray? = null

        override fun getIndicatorColor(position: Int): Int {
            return mIndicatorColors!![position % mIndicatorColors!!.size]
        }

        internal fun setIndicatorColors(vararg colors: Int) {
            mIndicatorColors = colors
        }
    }

    companion object {

        private val DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0
        private val DEFAULT_BOTTOM_BORDER_COLOR_ALPHA: Byte = 0x26
        private val SELECTED_INDICATOR_THICKNESS_DIPS = 3
        private val DEFAULT_SELECTED_INDICATOR_COLOR = -0xcc4a1b

        private fun setColorAlpha(color: Int, alpha: Byte): Int {
            return Color.argb(alpha.toInt(), Color.red(color), Color.green(color), Color.blue(color))
        }

        private fun blendColors(color1: Int, color2: Int, ratio: Float): Int {
            val inverseRation = 1f - ratio
            val r = Color.red(color1) * ratio + Color.red(color2) * inverseRation
            val g = Color.green(color1) * ratio + Color.green(color2) * inverseRation
            val b = Color.blue(color1) * ratio + Color.blue(color2) * inverseRation
            return Color.rgb(r.toInt(), g.toInt(), b.toInt())
        }
    }
}
