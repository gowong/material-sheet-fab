package com.gordonwong.materialsheetfab.sample;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.gordonwong.materialsheetfab.AnimatedFab;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Sample floating action button implementation.
 */
public class Fab extends FloatingActionButton implements AnimatedFab {

	public Fab(Context context) {
		super(context);
	}

	public Fab(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Fab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Shows the FAB.
	 */
	@Override
	public void show() {
		show(0, 0);
	}

	/**
	 * Shows the FAB and sets the FAB's translation.
	 *
	 * @param translationX translation X value
	 * @param translationY translation Y value
	 */
	@Override
	public void show(float translationX, float translationY) {
		setVisibility(View.VISIBLE);
	}

	/**
	 * Hides the FAB.
	 */
	@Override
	public void hide() {
		setVisibility(View.GONE);
	}
}
