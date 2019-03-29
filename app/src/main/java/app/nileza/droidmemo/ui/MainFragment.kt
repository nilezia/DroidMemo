package app.nileza.droidmemo.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.nileza.droidmemo.R
import app.nileza.droidmemo.adapter.TabViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val loginListener: FragmentInterFace? = null


    interface FragmentInterFace {
        fun setTitle(title: String)
    }

    fun newInstance(): MainFragment {

        val args = Bundle()

        val fragment = MainFragment()
        fragment.arguments = args
        return fragment
    }

    companion object {
        fun newInstance(): MainFragment {

            val args = Bundle()

            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //  initInstance(rootView)

        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInstance(view!!)
    }

    private fun initInstance(rootView: View) {

        //  viewPager = rootView.findViewById(R.id.viewPager)
        // tabLayout = rootView.findViewById(R.id.tabLayout)

        tabLayout.addTab(tabLayout!!.newTab().setText("Feed"))
        tabLayout.addTab(tabLayout!!.newTab().setText("MyPost"))


        tabLayout.addOnTabSelectedListener(tabSelectedListener)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        viewPager.adapter = TabViewPagerAdapter(childFragmentManager)

        //        loginListener = (FragmentInterFace) getActivity();
        //        loginListener.setTitle((String) tabLayout.getTabAt(0).getText());

    }

    private var tabSelectedListener: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager!!.currentItem = tab.position
            //  loginListener.setTitle((String) tab.getText());

        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }
    }
}