package com.example.mymvi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class NetworkClient {
    
    private val BASE_URL = "https://itunes.apple.com/"
    
    lateinit var retrofit : Retrofit
    
    companion object {
        
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkClient()
        }
    }
    
    init {
       retrofit= create()
    }
    
    private fun create() : Retrofit{
        var builder = OkHttpClient.Builder()
                .connectTimeout(10 , TimeUnit.SECONDS)
                .writeTimeout(10 , TimeUnit.SECONDS)
                .readTimeout(30 , TimeUnit.SECONDS)
        var httpLoggingInterceptor = HttpLoggingInterceptor()
        
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        var client = builder.addInterceptor(httpLoggingInterceptor)
                .readTimeout(10 , TimeUnit.SECONDS)
                .connectTimeout(10 , TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        return retrofit
    }
    
}

//search?term=歌&limit=200&country=HK
interface ApiService {
    
    @GET("search")
    suspend fun searchSong(
            @Query("term") term : String ,
            @Query("country") country : String ,
            @Query("limit") limit : Int
                          ) : DataResult<List<Song>>
}

