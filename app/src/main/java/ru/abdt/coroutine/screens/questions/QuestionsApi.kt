package ru.abdt.coroutine.screens.questions

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionsApi {

    @GET("questions")
    fun getQuestions(@Query("pagesize") pageSize: Int, @Query("order") order: String,
                     @Query("sort") sort: String, @Query("site") site: String): Call<QuestionsResponseModel>


}
