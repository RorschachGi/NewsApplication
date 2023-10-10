package com.example.newsapplication.data.api

import com.example.newsapplication.models.NewsResponse
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {

    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadLines(countryCode = countryCode, page = pageNumber)


    suspend fun searchNews(query: String,pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)


}