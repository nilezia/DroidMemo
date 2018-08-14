package app.nileza.droidmemo.utils

import android.content.Context

/**
 * Created by NileZia on 9/29/2017 AD.
 */
class Contextor {

    lateinit var context: Context

    private object getInstance {
        val INSTANCE = Contextor()
    }

    companion object {
        val instance: Contextor by lazy { getInstance.INSTANCE }
    }


}