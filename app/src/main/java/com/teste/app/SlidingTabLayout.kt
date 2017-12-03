package com.teste.app

import android.content.Context
import android.graphics.Typeface
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView

class SlidingTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : HorizontalScrollView(context, attrs, defStyle) {

    private var mViewPager: ViewPager? = null
    private val mTitleOffset: Int
    private var mTabViewLayoutId: Int = 0
    private var mTabViewTextViewId: Int = 0
    private var mDistributeEvenly: Boolean = false
    private val mContentDescriptions = SparseArray<String>()
    private var mViewPagerPageChangeListener: ViewPager.OnPageChangeListener? = null

    private val mTabStrip: SlidingTabStrip

    interface TabColorizer {
        fun getIndicatorColor(position: Int): Int
    }

    init {
        isHorizontalScrollBarEnabled = false
        isFillViewport = true

        mTitleOffset = (TITLE_OFFSET_DIPS * resources.displayMetrics.density).toInt()

        mTabStrip = SlidingTabStrip(context)
        addView(mTabStrip, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }

    fun setCustomTabColorizer(tabColorizer: TabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer)
    }

    fun setDistributeEvenly(distributeEvenly: Boolean) {
        mDistributeEvenly = distributeEvenly
    }

    fun setSelectedIndicatorColors(vararg colors: Int) {
        mTabStrip.setSelectedIndicatorColors(*colors)
    }

    fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        mViewPagerPageChangeListener = listener
    }

    fun setCustomTabView(layoutResId: Int, textViewId: Int) {
        mTabViewLayoutId = layoutResId
        mTabViewTextViewId = textViewId
    }

    fun setViewPager(viewPager: ViewPager?) {
        mTabStrip.removeAllViews()

        mViewPager = viewPager
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(InternalViewPagerListener())
            populateTabStrip()
        }
    }

    protected fun createDefaultTabView(context: Context): TextView {
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP.toFloat())
        textView.typeface = Typeface.DEFAULT_BOLD
        textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val outValue = TypedValue()
        getContext().theme.resolveAttribute(android.R.attr.selectableItemBackground,
                outValue, true)
        textView.setBackgroundResource(outValue.resourceId)
        textView.setAllCaps(true)

        val padding = (TAB_VIEW_PADDING_DIPS * resources.displayMetrics.density).toInt()
        textView.setPadding(padding, padding, padding, padding)

        return textView
    }

    private fun populateTabStrip() {
        val adapter = mViewPager!!.adapter
        val tabClickListener = TabClickListener()

        if (adapter != null) {
            for (i in 0 until adapter.count) {
                var tabView: View? = null
                var tabTitleView: TextView? = null

                if (mTabViewLayoutId != 0) {
                    tabView = LayoutInflater.from(context).inflate(
                            mTabViewLayoutId,
                            mTabStrip,
                            false
                    )
                    tabTitleView = tabView!!.findViewById<View>(mTabViewTextViewId) as TextView
                }

                if (tabView == null) {
                    tabView = createDefaultTabView(context)
                }

                if (tabTitleView == null && TextView::class.java.isInstance(tabView)) {
                    tabTitleView = tabView as TextView?
                }

                if (mDistributeEvenly) {
                    val lp = tabView.layoutParams as LinearLayout.LayoutParams
                    lp.width = 0
                    lp.weight = 1f
                }

                if (tabTitleView != null) {
                    tabTitleView.text = adapter.getPageTitle(i)
                }

                tabView.setOnClickListener(tabClickListener)
                val desc = mContentDescriptions.get(i, null)
                if (desc != null) {
                    tabView.contentDescription = desc
                }

                mTabStrip.addView(tabView)
                if (i == mViewPager!!.currentItem) {
                    tabView.isSelected = true
                }
            }
        }
    }

    fun setContentDescription(i: Int, desc: String) {
        mContentDescriptions.put(i, desc)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (mViewPager != null) {
            scrollToTab(mViewPager!!.currentItem, 0)
        }
    }

    private fun scrollToTab(tabIndex: Int, positionOffset: Int) {
        val tabStripChildCount = mTabStrip.childCount
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return
        }

        val selectedChild = mTabStrip.getChildAt(tabIndex)
        if (selectedChild != null) {
            var targetScrollX = selectedChild.left + positionOffset

            if (tabIndex > 0 || positionOffset > 0) {
                targetScrollX -= mTitleOffset
            }

            scrollTo(targetScrollX, 0)
        }
    }

    private inner class InternalViewPagerListener : ViewPager.OnPageChangeListener {
        private var mScrollState: Int = 0

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            val tabStripChildCount = mTabStrip.childCount
            if (tabStripChildCount == 0 || position < 0 || position >= tabStripChildCount) {
                return
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset)

            val selectedTitle = mTabStrip.getChildAt(position)
            val extraOffset = if (selectedTitle != null)
                (positionOffset * selectedTitle.width).toInt()
            else
                0
            scrollToTab(position, extraOffset)

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener!!.onPageScrolled(position, positionOffset,
                        positionOffsetPixels)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            mScrollState = state

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener!!.onPageScrollStateChanged(state)
            }
        }

        override fun onPageSelected(position: Int) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f)
                scrollToTab(position, 0)
            }
            for (i in 0 until mTabStrip.childCount) {
                mTabStrip.getChildAt(i).isSelected = position == i
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener!!.onPageSelected(position)
            }
        }

    }

    private inner class TabClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            for (i in 0 until mTabStrip.childCount) {
                if (v === mTabStrip.getChildAt(i)) {
                    mViewPager!!.currentItem = i
                    return
                }
            }
        }
    }

    companion object {

        private val TITLE_OFFSET_DIPS = 24
        private val TAB_VIEW_PADDING_DIPS = 16
        private val TAB_VIEW_TEXT_SIZE_SP = 12
    }

}
