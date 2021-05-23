package com.example.testimegeapp.ui.list

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.testimegeapp.R
import com.example.testimegeapp.databinding.FragmentListBinding
import com.example.testimegeapp.ui.items.ImageItem
import com.example.testimegeapp.ui.models.Photo
import com.example.testimegeapp.utils.viewBinding
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {
    private val binding by viewBinding(FragmentListBinding::bind)
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel by viewModels<ListFragmentViewModel>()
    var text: String = ""
    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            gLayout.applySystemWindowInsetsToPadding(top = true)
            recyclerView.applySystemWindowInsetsToPadding(bottom = true)
            recyclerView.apply {
                if (adapter != null) return@apply
                adapter = this@ListFragment.adapter
            }
            textInputEditText.doAfterTextChanged {
                text = it.toString()
            }
            binding.btnSearch.setOnClickListener {
                viewModel.search(text)
            }
        }
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                photosLiveData.observe(viewLifecycleOwner, ::assembleHome)
            }
        }
    }

    private fun assembleHome(photos: List<Photo>) {
        val uiList = mutableListOf<Group>()
        photos.forEach {
            uiList += ImageItem(photoUrl = it.url) {
                viewModel.goImage(
                        url = it.url,
                        title = it.title
                )
            }
        }

        adapter.updateAsync(uiList)
    }
}