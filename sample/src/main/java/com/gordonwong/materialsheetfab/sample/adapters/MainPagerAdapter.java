package com.gordonwong.materialsheetfab.sample.adapters;

import android.content.Context;

import com.gordonwong.materialsheetfab.sample.R;
import com.gordonwong.materialsheetfab.sample.fragments.AllFragment;
import com.gordonwong.materialsheetfab.sample.fragments.FavoritesFragment;
import com.gordonwong.materialsheetfab.sample.fragments.SharedFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Pager adapter for main activity.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

	public static final int NUM_ITEMS = 3;
	public static final int ALL_POS = 0;
	public static final int SHARED_POS = 1;
	public static final int FAVORITES_POS = 2;

	private final Context context;

	public MainPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case ALL_POS:
			return AllFragment.newInstance();
		case SHARED_POS:
			return SharedFragment.newInstance();
		case FAVORITES_POS:
			return FavoritesFragment.newInstance();
		default:
			return null;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case ALL_POS:
			return context.getString(R.string.all);
		case SHARED_POS:
			return context.getString(R.string.shared);
		case FAVORITES_POS:
			return context.getString(R.string.favorites);
		default:
			return "";
		}
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}
}
