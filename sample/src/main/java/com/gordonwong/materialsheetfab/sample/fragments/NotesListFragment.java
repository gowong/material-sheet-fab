package com.gordonwong.materialsheetfab.sample.fragments;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gordonwong.materialsheetfab.sample.R;
import com.gordonwong.materialsheetfab.sample.adapters.NotesAdapter;

/**
 * Created by Gordon Wong on 7/18/2015.
 *
 * Generic fragment displaying a list of notes.
 */
public abstract class NotesListFragment extends Fragment {

	@LayoutRes
	protected abstract int getLayoutResId();

	protected abstract int getNumColumns();

	protected abstract int getNumItems();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutResId(), container, false);

		// Setup list
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getNumColumns(),
				StaggeredGridLayoutManager.VERTICAL));
		recyclerView.setAdapter(new NotesAdapter(getActivity(), getNumItems()));

		return view;
	}

}
