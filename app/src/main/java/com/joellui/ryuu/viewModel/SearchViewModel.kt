package com.joellui.ryuu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joellui.ryuu.model.AnimeList
import com.joellui.ryuu.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: Repository): ViewModel() {

    val searchResult: MutableLiveData<Response<AnimeList>> = MutableLiveData()

    fun getSearchAnime(
        Title:String? = null,
        Formats: String? = null,
        Status:String? = null,
        Year:Int? = null,
        Season:Int? = null,
        Genres:String? = null,
    ){
        viewModelScope.launch {
            val response = repository.getSearchAnime(Title,Formats,Status,Year,Season,Genres)
            searchResult.value = response
        }
    }
}