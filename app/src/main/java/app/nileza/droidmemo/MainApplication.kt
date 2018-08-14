package app.nileza.droidmemo

import android.app.Application
import app.nileza.droidmemo.utils.Contextor

/**
 * Created by NileZia on 29/12/2017 AD.
 */

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Contextor.instance.context = applicationContext
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}
