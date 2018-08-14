package app.nileza.droidmemo.ui.feed.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import app.nileza.droidmemo.R
import app.nileza.droidmemo.data.PostMessage
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_group_all_note.*
import java.util.*
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy


/**
 * Created by Nougat on 5/6/2017.
 */

class NoteViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
    private var mUser: FirebaseUser? = null
/*
    val tvTitle: TextView? = itemView.findViewById(R.id.tv_title) as TextView?
    val tvBody: TextView? = itemView.findViewById(R.id.tv_body) as TextView?
    val tvDate: TextView? = itemView.findViewById(R.id.tvCreateTime) as TextView?
    val imgPost: ImageView? = itemView.findViewById(R.id.img_post) as ImageView?
    val imgUser: ImageView? = itemView.findViewById(R.id.img_user) as ImageView?
    val tvId: TextView? = itemView.findViewById(R.id.tvID) as TextView?
*/

    fun bind(dao: PostMessage?, context: Context) {

        dao.let {

            val link = dao?.link
            tvTitle?.text = dao?.title
            tvBody?.text = dao?.body

            tvCreateTime?.text = Date(dao?.date!!.toLong()).toString()

            tvID?.text = dao.name
            if (!dao.avatar.isNullOrBlank()) {
                Glide.with(context).load(dao.avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .fitCenter().crossFade()
                        .fitCenter()
                        .into(imgUser)
            }

            if (link.isNullOrEmpty()) {
                imgPost!!.visibility = View.GONE
            } else {
                imgPost!!.visibility = View.VISIBLE
                Glide.with(context)
                        .load(link)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder).error(R.drawable.load_fail)
                        .fitCenter()
                        .crossFade().into(imgPost)
            }
        }
    }

    private fun getUser(): FirebaseUser? {
        mUser = FirebaseAuth.getInstance().currentUser
        return mUser
    }
}