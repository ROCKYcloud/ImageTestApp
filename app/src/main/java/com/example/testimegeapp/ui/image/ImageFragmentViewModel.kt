package com.example.testimegeapp.ui.image

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.testimegeapp.utils.NavigationDispatcher
import javax.inject.Inject

class ImageFragmentViewModel  @Inject constructor(
    private val handle: SavedStateHandle,
    private val navigationDispatcher: NavigationDispatcher) : ViewModel() {
}