package app.nileza.droidmemo.manager

import app.nileza.droidmemo.data.DataCollection

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by NileZia on 29/12/2017 AD.
 */

interface ApiService {

    @GET("listhero/posts")
    fun getAllPost( @Query("userId") userId:String ): Call<DataCollection>


}