package app.nileza.droidmemo.ui.feed

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.nileza.droidmemo.R
import app.nileza.droidmemo.ui.feed.adapter.NoteAdapter
import app.nileza.droidmemo.data.PostMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_feed.*


/**
 * Created by Nougat on 5/6/2017.
 */

class FeedDirectFireBaseFragment : Fragment() {

    private lateinit var adapter: NoteAdapter
    private var mDatabase: DatabaseReference? = null
    private var mPost: Query? = null
    private var valuesEventListener: ValueEventListener? = null
    private var childEventListener: ChildEventListener? = null
    //private lateinit var addList: MutableList<Messages>
    private lateinit var addList: MutableList<PostMessage>

    // private var ListId: MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    companion object {
        fun newInstance(): FeedDirectFireBaseFragment {

            val args = Bundle()

            val fragment = FeedDirectFireBaseFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFirebase()
        initInstance()
        showProgressDialog()

    }

    private fun setupFirebase() {
        val mUser = FirebaseAuth.getInstance().currentUser
        mDatabase = FirebaseDatabase.getInstance().reference

        mPost = mDatabase?.child("post")?.child(mUser?.uid)
        //  mPost = mPost?.orderByChild("date")
        addList = mutableListOf()

    }


    override fun onStart() {
        super.onStart()

        // ListId = mutableListOf()
/*
        valuesEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                val children = p0?.children
                addList = mutableListOf()
                while (children?.iterator()!!.hasNext()) {
                    val key = children.iterator().next().key
                    val messages = p0.child(key).getValue(Messages::class.java)
                    addList?.add(messages)

                }
                Log.d("testData", addList.toString())
                adapter.daos = addList
                adapter.notifyDataSetChanged()
            }

        }
*/

        childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                val messages = p0?.getValue(PostMessage::class.java)

                val key: String? = p0?.key
                val postIndex = addList.find {

                    it._id == key
                }

                addList.add(postIndex!!)
                adapter.daos = addList
                adapter.notifyDataSetChanged()

                Log.d("testData", "Child Changed ${messages?.title}")
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {

                val messages = p0?.getValue(PostMessage::class.java)

                addList = adapter.daos!!
                messages?.let {

                    it._id = p0.key
                    addList.add(it)

                }
                //  p0?.key?.let { ListId?.add(it) }


//                Collections.sort(ListId, Collections.reverseOrder())
//                Collections.reverse(addList)
//                ListId!!.forEach { Log.d("testData", "ListId After Sort $it ") }

                addList.sortByDescending { item ->
                    item.date
                }
                //  addList!!.forEach { Log.d("testData", "addList After Sort ${it.title} ") }

                adapter.daos = addList
                adapter.notifyItemInserted(adapter.daos!!.size - 1)

                hideProgressDialog()
            }


            override fun onChildRemoved(p0: DataSnapshot?) {
                val messages = p0?.getValue(PostMessage::class.java)

                val key: String? = p0?.key
                val postIndex = addList.find {
                    it._id == key
                }

                Log.d("testData", "Child Removed ${messages?.title} $postIndex  Key : $key")


                // Remove data from the list
                addList.remove(postIndex)

                adapter.daos = addList
                adapter.notifyDataSetChanged()
                // Update the RecyclerView
                // adapter.notifyItemRemoved(postIndex._id)

            }
        }
        mPost?.addChildEventListener(childEventListener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onStop() {
        super.onStop()

        //mPost?.removeEventListener(valuesEventListener)
        mPost?.removeEventListener(childEventListener)
    }

    private fun initInstance() {

        adapter = NoteAdapter()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        // layoutManager.reverseLayout = true
        //  layoutManager.stackFromEnd = true
        recycleView.layoutManager = layoutManager
        recycleView.adapter = adapter


    }

    private var mProgressDialog: ProgressDialog? = null

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(activity)
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setMessage("Loading...")
            mProgressDialog!!.isIndeterminate = true
        }

        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        hideProgressDialog()
    }

}
