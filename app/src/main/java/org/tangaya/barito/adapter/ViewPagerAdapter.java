package org.tangaya.barito.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.tangaya.barito.view.ui.HeadlineFragment;
import org.tangaya.barito.view.ui.SourcesFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        childFragments = new Fragment[] {
                new HeadlineFragment(),
                new SourcesFragment()
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
                return "Headline";
            case 1:
                return "By Source";
            default:
                return "unknown";
        }
    }
}
