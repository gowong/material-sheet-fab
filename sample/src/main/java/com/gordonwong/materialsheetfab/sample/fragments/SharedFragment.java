package com.gordonwong.materialsheetfab.sample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gordonwong.materialsheetfab.sample.R;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Shared items fragment.
 */
public class SharedFragment extends Fragment {

	public static SharedFragment newInstance() {
		return new SharedFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_shared, container, false);
	}
}
