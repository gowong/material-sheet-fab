package com.gordonwong.materialsheetfab.sample;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Main activity for material sheet fab sample.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private ActionBarDrawerToggle drawerToggle;
	private DrawerLayout drawerLayout;
	private MaterialSheetFab materialSheetFab;
	private int statusBarColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.notes);
		setContentView(R.layout.activity_main);
		setupActionBar();
		setupDrawer();
		setupTabs();
		setupFab();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	public void onBackPressed() {
		if (materialSheetFab.isSheetVisible()) {
			materialSheetFab.hideSheet();
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * Sets up the action bar.
	 */
	private void setupActionBar() {
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/**
	 * Sets up the navigation drawer.
	 */
	private void setupDrawer() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer,
				R.string.closedrawer);
		drawerLayout.setDrawerListener(drawerToggle);
	}

	/**
	 * Sets up the tabs.
	 */
	private void setupTabs() {
		// Setup view pager
		ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
		viewpager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()));

		// Setup tab layout
		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(viewpager);
	}

	/**
	 * Sets up the Floating action button.
	 */
	private void setupFab() {

		Fab fab = (Fab) findViewById(R.id.fab);
		View sheetView = findViewById(R.id.fab_sheet);
		View overlay = findViewById(R.id.overlay);
		int sheetColor = getResources().getColor(R.color.background_card);
		int fabColor = getResources().getColor(R.color.theme_accent);

		// Create material sheet FAB
		materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

		// Set material sheet event listener
		materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
			@Override
			public void onShowSheet() {
				// Save current status bar color
				statusBarColor = getStatusBarColor();
				// Set darker status bar color to match the dim overlay
				setStatusBarColor(getResources().getColor(R.color.theme_primary_dark2));
			}

			@Override
			public void onHideSheet() {
				// Restore status bar color
				setStatusBarColor(statusBarColor);
			}
		});

		// Set material sheet item click listeners
		findViewById(R.id.fab_sheet_item_recording).setOnClickListener(this);
		findViewById(R.id.fab_sheet_item_reminder).setOnClickListener(this);
		findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
		findViewById(R.id.fab_sheet_item_note).setOnClickListener(this);
	}

	/**
	 * Toggles opening/closing the drawer.
	 */
	private void toggleDrawer() {
		if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			drawerLayout.openDrawer(GravityCompat.START);
		}
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(this, R.string.sheet_item_pressed, Toast.LENGTH_SHORT).show();
		materialSheetFab.hideSheet();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggleDrawer();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private int getStatusBarColor() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return getWindow().getStatusBarColor();
		}
		return 0;
	}

	private void setStatusBarColor(int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setStatusBarColor(color);
		}
	}
}
