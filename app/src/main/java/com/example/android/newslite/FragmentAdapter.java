package com.example.android.newslite;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android.newslite.HomeFragments.AFragment;
import com.example.android.newslite.HomeFragments.BFragment;
import com.example.android.newslite.HomeFragments.CFragment;
import com.example.android.newslite.HomeFragments.DFragment;
import com.example.android.newslite.HomeFragments.EFragment;
import com.example.android.newslite.HomeFragments.GFragment;
import com.example.android.newslite.HomeFragments.HFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public FragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0: return new AFragment();
            case 1: return new BFragment();
            case 2: return new CFragment();
            case 3: return new DFragment();
            case 4: return new EFragment();
            case 5: return new Fragment();
            case 6: return new GFragment();
            case 7: return new HFragment();
        }
        return new AFragment();
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
