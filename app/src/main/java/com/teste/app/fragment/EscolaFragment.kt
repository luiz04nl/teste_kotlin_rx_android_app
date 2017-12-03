package com.teste.app.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teste.app.R.layout.fragment_escola

class EscolaFragment : Fragment() {

    /**
     * Método executado na criação da view do fragmento
     *
     * @param layoutInflater
     * @param viewGroup
     * @param bundle
     * @return View?
     */
    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(fragment_escola, viewGroup, false)
    }

}
