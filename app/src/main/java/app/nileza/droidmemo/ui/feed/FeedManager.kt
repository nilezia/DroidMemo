package app.nileza.droidmemo.ui.feed

import android.annotation.SuppressLint
import android.util.Log
import app.nileza.droidmemo.data.DataCollection
import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.manager.HttpManager
import app.nileza.droidmemo.ui.base.ServiceCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by NileZia on 29/12/2017 AD.
 */


class FeedManager : FeedContractManager {

    @SuppressLint("RestrictedApi")
    override fun getAllPost(callback: ServiceCallback<MutableList<PostMessage>>) {

        HttpManager.instance.service.getAllPost(FirebaseAuth.getInstance().uid!!).enqueue(object : Callback<DataCollection> {
            override fun onResponse(call: Call<DataCollection>?, response: Response<DataCollection>?) {

                if (response?.isSuccessful!!) {


                    response.body()?.data?.let { it ->
                        Log.d("Toas", "Data: ${Gson().toJson(it)}")
                        callback.onSuccess(it)


                    }
                    if (response.body()?.data == null)
                        getAllPost(callback)


                }
            }

            override fun onFailure(call: Call<DataCollection>?, t: Throwable?) {
                Log.d("Toas", t.toString())
            }
        })


    }
}