package com.joellui.ryuu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joellui.ryuu.model.Anime
import com.joellui.ryuu.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class AnimeDetailsViewModel(private val repository: Repository): ViewModel() {

    val AnimeInfo: MutableLiveData<Response<Anime>> = MutableLiveData()

    fun getAnime(id: Int){
        viewModelScope.launch {
            val response = repository.getAnime(id)
            AnimeInfo.value = response
        }
    }

}