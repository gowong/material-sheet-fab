package com.gordonwong.materialsheetfab.sample.fragments;

import com.gordonwong.materialsheetfab.sample.R;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Shared items fragment.
 */
public class SharedFragment extends NotesListFragment {

	public static SharedFragment newInstance() {
		return new SharedFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_shared;
	}

	@Override
	protected int getNumColumns() {
		return 2;
	}

	@Override
	protected int getNumItems() {
		return 10;
	}
}
