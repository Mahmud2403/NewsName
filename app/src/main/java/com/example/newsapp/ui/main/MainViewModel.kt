package com.example.newsapp.ui.main

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
class MainViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

	val newsLiveData: MutableLiveData<Resourse<NewsResponse>> = MutableLiveData()
	var newsPage = 1

	init {
		getNews("ru")
	}

	private fun getNews(countryCode: String){
		viewModelScope.launch {
			newsLiveData.postValue(Resourse.Loading())
			val response = repository.getNews(countryCode = countryCode, pageNumber = newsPage)
			if (response.isSuccessful){
				response.body().let {
					newsLiveData.postValue(Resourse.Success(it))
				}
			}else{
				newsLiveData.postValue(Resourse.Error(message = response.message()))
			}
		}
	}
}