package com.example.testimegeapp.ui.list

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testimegeapp.R
import com.example.testimegeapp.networking.WebClient
import com.example.testimegeapp.ui.models.Photo
import com.example.testimegeapp.utils.NavigationDispatcher
import com.example.testimegeapp.utils.tryOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModel @Inject constructor(
    private val navigationDispatcher: NavigationDispatcher,
) : ViewModel() {
    private val CODE_OK = 200
    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    init {
        fetchImages("water")
    }

    private fun fetchImages(tags:String) = viewModelScope.launch(Dispatchers.IO) {
        val searchResponse = WebClient.client.fetchImages(tags)
        val result = tryOrNull { searchResponse }
        when {
            result == null -> {
                //TODO Error
                Log.d("photosLiveData","Error")
            }
            result.code() == CODE_OK -> {
                val photosList = result.body()!!.photos.photo.map { photo ->
                    Photo(
                        id = photo.id,
                        url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                        title = photo.title
                    )
                }
                photosLiveData.postValue(photosList)
                Log.d("photosLiveData","${photosLiveData}")
            }
            else -> {
                Log.d("photosLiveData","Error")
//TODO Error
            }
        }
    }

fun search(tags: String){
    fetchImages(tags)
}
    fun goImage(url: String, title:String) = navigationDispatcher.emit {
        it.navigate(R.id.goImage, Bundle().apply {
            putString("title",title)
            putString("url", url)

        })
    }
}