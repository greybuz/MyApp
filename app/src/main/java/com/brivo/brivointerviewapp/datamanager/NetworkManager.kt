package com.brivo.brivointerviewapp.datamanager

import com.brivo.brivointerviewapp.model.School
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class NetworkManager {
    private var service: ISchoolService

    val url = "https://data.cityofnewyork.us/"

    init {
        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder()
                .build()

        val instance: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = instance.create(ISchoolService::class.java)
    }

    fun getSchools(
        listener: IOnGetSchoolsListener
    ) {
        val request = service.getSchools()
        request.enqueue(object : Callback<List<School>> {
            override fun onResponse(
                call: Call<List<School>>,
                response: Response<List<School>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { listener.onSuccess(it) }
                } else {
                    listener.onFailed(response.message())
                }
            }

            override fun onFailure(call: Call<List<School>>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }

    interface ISchoolService {
        @GET("resource/s3k6-pzi2.json")
        fun getSchools(): Call<List<School>>
    }

    interface IOnGetSchoolsListener {
        fun onSuccess(schools: List<School>)
        fun onFailed(error: String?)
    }
}