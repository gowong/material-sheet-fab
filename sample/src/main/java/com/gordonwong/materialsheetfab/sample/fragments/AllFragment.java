package com.gordonwong.materialsheetfab.sample.fragments;

import com.gordonwong.materialsheetfab.sample.R;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * All items fragment.
 */
public class AllFragment extends NotesListFragment {

	public static AllFragment newInstance() {
		return new AllFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_all;
	}

	@Override
	protected int getNumColumns() {
		return 2;
	}

	@Override
	protected int getNumItems() {
		return 20;
	}
}
