package com.teste.app.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teste.app.R.layout.fragment_confira

class ConfiraFragment : Fragment() {

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup?, bundle: Bundle?): View? {
        return layoutInflater.inflate(fragment_confira, viewGroup, false)
    }

}

