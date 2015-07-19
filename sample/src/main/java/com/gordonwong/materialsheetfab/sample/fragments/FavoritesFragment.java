package com.gordonwong.materialsheetfab.sample.fragments;

import com.gordonwong.materialsheetfab.sample.R;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Favorite items fragment.
 */
public class FavoritesFragment extends NotesListFragment {

	public static FavoritesFragment newInstance() {
		return new FavoritesFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_favorites;
	}

	@Override
	protected int getNumColumns() {
		return 1;
	}

	@Override
	protected int getNumItems() {
		return 7;
	}
}
