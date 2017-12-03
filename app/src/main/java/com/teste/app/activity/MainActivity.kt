package com.teste.app.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.teste.app.R
import com.teste.app.SlidingTabLayout
import com.teste.app.adapter.pager.FeedPagerAdapter

class MainActivity : AppCompatActivity() {

    private var mViewPager: ViewPager? = null
    private var mSlidingTabLayout: SlidingTabLayout? = null

    /**
     * Método executado na criação da activity principal
     *
     * @param bundle
     * @return
     */
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)
        this.attachUI()
    }

    /**
     * Método que anexa os componentes de UI a classe atual
     *
     * @return
     */
    private fun attachUI() {
        this.mViewPager = findViewById(R.id.view_pager)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(R.color.White))

        this.mSlidingTabLayout = findViewById(R.id.stl_tabs)
        this.mSlidingTabLayout!!.setSelectedIndicatorColors(resources.getColor(R.color.White))
        this.mSlidingTabLayout!!.setCustomTabView(R.layout.tab_view, R.id.tv_tab)

        val fragmentManager = fragmentManager
        this.mViewPager!!.adapter = FeedPagerAdapter(fragmentManager, this)
        this.setSlidingTabLayout()

        this.setSupportActionBar(toolbar)
        val actionbar = supportActionBar

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setTitle(R.string.app_name)
        }
    }

    /**
     * Método que adiciona o listener de pagina auterada ao layout de tabs
     *
     * @return
     */
    fun setSlidingTabLayout() {
        this.mSlidingTabLayout!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
        this.mSlidingTabLayout!!.setViewPager(mViewPager)
    }

}
