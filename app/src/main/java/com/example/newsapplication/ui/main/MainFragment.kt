package com.example.newsapplication.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.databinding.FragmentDetailsBinding


class MainFragment : Fragment() {

    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

}