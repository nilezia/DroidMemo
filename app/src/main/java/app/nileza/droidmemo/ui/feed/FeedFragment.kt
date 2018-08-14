package app.nileza.droidmemo.ui.feed

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.nileza.droidmemo.R
import app.nileza.droidmemo.data.PostMessage
import app.nileza.droidmemo.mvp.BaseMvpFragment
import app.nileza.droidmemo.ui.feed.adapter.NoteAdapter
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * Created by NileZia on 29/12/2017 AD.
 */


class FeedFragment : BaseMvpFragment<FeedInterface.View, FeedPresenter>(), FeedInterface.View {


    override var mPresenter: FeedPresenter = FeedPresenter()


    private var adapter: NoteAdapter? = null

    companion object {
        fun newInstance() = FeedFragment().apply {
            arguments = Bundle()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_feed, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bindView(view)
        showProgressDialog()
        mPresenter.loadFeedData()
    }

    override fun bindView(view: View?) {
        adapter = NoteAdapter()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycleView.layoutManager = layoutManager
        recycleView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            mPresenter.loadFeedData()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onPostsUpdate(posts: MutableList<PostMessage>) {
        posts.sortByDescending {
            it.date
        }
//        val position = adapter?.daos?.indexOf(posts[1])
//        adapter?.daos?.set(position!!, posts[1])

        adapter?.daos = posts
        adapter?.notifyDataSetChanged()

        swipeRefresh.isRefreshing = false
        hideProgressDialog()
    }


    override fun showEmptyView() {

        recycleView.visibility = View.INVISIBLE
        emptyView.visibility = View.GONE
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

    }
}