package org.tangaya.barito.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.tangaya.barito.view.ui.BookmarkFragment;
import org.tangaya.barito.view.ui.HeadlineFragment;
import org.tangaya.barito.view.ui.SearchFragment;

public class ViewPagerAdapter2 extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter2(FragmentManager fm) {
        super(fm);

        childFragments = new Fragment[] {
                new HeadlineFragment(),
                new SearchFragment(),
                new BookmarkFragment()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "business";
            case 1:
                return "entertainment";
            case 2:
                return "general";
            case 3:
                return "health";
            case 4:
                return "science";
            case 5:
                return "sport";
            case 6:
                return "technology";
            default:
                return "All";
        }
    }
}
