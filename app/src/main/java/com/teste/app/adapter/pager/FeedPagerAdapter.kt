package com.teste.app.adapter.pager

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.v13.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.teste.app.R
import com.teste.app.fragment.*

class FeedPagerAdapter(fragmentManager: FragmentManager, context: Context)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val mTitles: Array<String> = arrayOf(context.getString(R.string.todos).toUpperCase(),
            context.getString(R.string.escola).toUpperCase(),
            context.getString(R.string.avisos).toUpperCase(),
            context.getString(R.string.biz).toUpperCase(),
            context.getString(R.string.confira).toUpperCase())

    /**
     * Método que retorna um item do adaptador pelo id
     *
     * @param position
     * @return Fragment?
     */
    override fun getItem(position: Int): Fragment? {
        var frag: Fragment? = null
        when (position) {
            0 -> {
                frag = TodosFragment()
            }
            1 -> {
                frag = EscolaFragment()
            }
            2 -> {
                frag = AvisosFragment()
            }
            3 -> {
                frag = BizFragment()
            }
            4 -> {
                frag = ConfiraFragment()
            }
            else -> {
                this.getItem(0)
            }
        }
        val b = Bundle()
        b.putInt("position", position)
        if (frag != null) {
            frag.arguments = b
        }
        return frag
    }

    /**
     * Método que retorna o tamanho da lista de itens do adaptador
     *
     * @returnInt
     */
    override fun getCount(): Int {
        return this.mTitles.size
    }

    /**
     * Método que retorna o titula da tab a partir da posição
     *
     * @param position
     * @return CharSequence?
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return this.mTitles[position]
    }

    /**
     * Método que remove aoremover item da adaptador
     *
     * @param container
     * @param position
     * @param `object`
     * @return
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (position >= count) {
            val manager = (`object` as Fragment).fragmentManager
            val trans = manager.beginTransaction()
            trans.remove(`object`)
            trans.commit()
        }
    }

}