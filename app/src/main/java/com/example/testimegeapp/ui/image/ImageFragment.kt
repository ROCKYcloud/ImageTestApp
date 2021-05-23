package com.example.testimegeapp.ui.image

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.testimegeapp.R
import com.example.testimegeapp.databinding.FragmentImageBinding
import com.example.testimegeapp.utils.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

@AndroidEntryPoint
class ImageFragment : Fragment(R.layout.fragment_image) {
    private val binding by viewBinding(FragmentImageBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            tvTitle.applySystemWindowInsetsToPadding(top = true)
            ivImage.load(arguments?.getString("url"))
            tvTitle.text = arguments?.getString("title")
            Log.d("ImageFragment","${arguments?.getString("title")}")
        }
    }
}