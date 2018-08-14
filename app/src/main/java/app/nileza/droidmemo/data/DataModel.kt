package app.nileza.droidmemo.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Nougat on 5/5/2017.
 */

data class User(var email: String? = "", var name: String? = "", var avatar: String? = "")

data class PostMessage(

        @field:SerializedName("id")
        var _id: String? = null,
        @field:SerializedName("title")
        var title: String? = null,
        @field:SerializedName("body")
        var body: String? = null,
        @field:SerializedName("link")
        var link: String? = null,
        @field:SerializedName("date")
        var date: String? = null,
        @field:SerializedName("name")
        var name: String? = null,
        @field:SerializedName("avatar")
        var avatar: String? = null,
        @field:SerializedName("uuid")
        var uuid: String? = null

)