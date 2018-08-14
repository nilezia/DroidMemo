package app.nileza.droidmemo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import app.nileza.droidmemo.ui.feed.FeedFragment


class TabViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {

            0 -> FeedFragment.newInstance()
            1 -> FeedFragment.newInstance()
            else -> FeedFragment.newInstance()
        }


    }

    override fun getCount(): Int {
        return 2
    }
}