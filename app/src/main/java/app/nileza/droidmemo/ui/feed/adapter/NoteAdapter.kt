package app.nileza.droidmemo.ui.feed.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.nileza.droidmemo.R
import app.nileza.droidmemo.data.PostMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by Nougat on 5/6/2017.
 */

class NoteAdapter : RecyclerView.Adapter<NoteViewHolder>() {

    // var daos: MutableList<Messages>? = null
    var daos: MutableList<PostMessage>? = mutableListOf()
    var mUser: FirebaseUser? = null
    lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        mContext = parent.context
        return NoteViewHolder(layoutInflater.inflate(R.layout.view_group_all_note, parent, false))
    }


    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        holder?.bind(daos?.get(position), mContext)
    }

    override fun getItemCount(): Int {
        if (daos == null)
            return 0

        return daos!!.size
    }


}