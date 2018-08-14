package app.nileza.droidmemo.ui.friend

import android.os.Bundle
import android.support.v4.app.Fragment

class FriendFragment : Fragment() {

    companion object {
        fun newInstance() = FriendFragment().apply {
            arguments = Bundle()

        }
    }
}