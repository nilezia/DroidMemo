package app.nileza.droidmemo.ui.friend

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class FriendActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FriendFragment.newInstance()
    }

}