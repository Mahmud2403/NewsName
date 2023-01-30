package com.example.newsapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.NewsRepository
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.utils.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {
	val searchNewsLiveData: MutableLiveData<Resourse<NewsResponse>> = MutableLiveData()
	var searchNewsPage = 1

	init {
		getSearchNews("")
	}

	fun getSearchNews(query: String){
		viewModelScope.launch {
			searchNewsLiveData.postValue(Resourse.Loading())
			val response = repository.searchNews(query, searchNewsPage)
			if(response.isSuccessful){
				response.body().let { res ->
					searchNewsLiveData.postValue(Resourse.Success(res))
				}
			}else{
				searchNewsLiveData.postValue(Resourse.Error(response.message()))
			}
		}
	}
}