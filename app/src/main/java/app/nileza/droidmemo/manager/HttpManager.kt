package app.nileza.droidmemo.manager

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by NileZia on 29/12/2017 AD.
 */

class HttpManager {

    lateinit var service: ApiService

    private var baseUrl = "http://us-central1-droidmemo-bfe09.cloudfunctions.net/"

    init {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(MyInterceptor())
        val client = builder.build()
        build(client)

    }

    private fun build(client: OkHttpClient) {

        // val baseUrlFromPref = PreferanceEx().getUrlServer()



        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

        service = retrofit.create(ApiService::class.java)
    }


    @Suppress("ClassName")
    private object getInstance {
        val INSTANCE = HttpManager()
    }

    companion object {
        val instance: HttpManager by lazy { getInstance.INSTANCE }
    }

}