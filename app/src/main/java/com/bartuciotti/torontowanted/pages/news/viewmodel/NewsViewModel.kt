package com.bartuciotti.torontowanted.pages.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bartuciotti.torontowanted.data.Repository
import com.bartuciotti.torontowanted.pages.news.data.News
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    fun getNews(): LiveData<List<News>> {
        return repository.getNews()
    }
}