package it.tabaccaiofurbo.com.tabaccaiofurboapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import it.tabaccaiofurbo.com.tabaccaiofurboapp.fragments.TabFragment;

/**
 * Created by 87001187 on 30/09/2016.
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public CustomPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        TabFragment tab = new TabFragment();
        tab.setId(position);
        //SETTO LA LISTA DEI
        //tab.setItemLIst(items[position]);
        /*switch (position) {
            case 0:
                tab.setId(position);
                break;
            case 1:
                TabFragment tab2 = new TabFragment();
                tab2.setId(position);
                return tab2;
            case 2:
                TabFragment tab3 = new TabFragment();
                tab3.setId(position);
                return tab3;
        }*/
        return tab;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

