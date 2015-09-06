package com.goodcodeforfun.cleancitybattery.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.content.ContentProvider;
import com.goodcodeforfun.cleancitybattery.CleanCityApplication;
import com.goodcodeforfun.cleancitybattery.R;
import com.goodcodeforfun.cleancitybattery.fragment.PointsListFragment;
import com.goodcodeforfun.cleancitybattery.fragment.PointsMapFragment;
import com.goodcodeforfun.cleancitybattery.model.Location;
import com.goodcodeforfun.cleancitybattery.network.NetworkService;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private static final int LOCATION_LOADER_ID = 1234;
    private final Handler mDrawerActionHandler = new Handler();
    private final PointsMapFragment mPointsMapFragment = new PointsMapFragment();
    private final PointsListFragment mPointsListFragment = new PointsListFragment();
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;
    private LoaderManager mLoaderManager;
    private LocationType mCurrentLocationType = LocationType.battery;
    private final LoaderManager.LoaderCallbacks<Cursor> mLocationLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            return new CursorLoader(MainActivity.this,
                    ContentProvider.createUri(Location.class, null),
                    null, "Type = ?", new String[]{mCurrentLocationType.name()}, null
            );

        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data.getCount() != 0) {
                LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                ArrayList<LatLng> mArrayList = new ArrayList<>();
                LatLng position;
                for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
                    position = new LatLng(data.getDouble(3), data.getDouble(4));
                    boundsBuilder.include(position);
                    mArrayList.add(position);
                }
                mPointsMapFragment.updateMap(mArrayList, boundsBuilder.build());
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }
    };

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        findViewById(R.id.menu_item_add_location).setOnClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // load saved navigation state if present
        if (null == savedInstanceState) {
            mNavItemId = R.id.drawer_item_1;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        // listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);
        setDrawerIndicator();
        // set up the hamburger icon to open and close the drawer
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mPointsMapFragment)
                .commit();
        navigate(mNavItemId);
        Intent mServiceGetIntent = new Intent(this, NetworkService.class);
        mServiceGetIntent.setAction(NetworkService.ACTION_GET_LIST_LOCATIONS);
        startService(mServiceGetIntent);

//        Location location = new Location();
//        location.setName("minaname");
//        location.setType("battery");
//        location.setLatitude(51.34);
//        location.setLongitude(24.26);
//        location.save();

//        Intent mServicePostIntent = new Intent(this, NetworkService.class);
//        mServicePostIntent.setAction(NetworkService.ACTION_POST_LOCATION);
//        mServicePostIntent.putExtra(NetworkService.EXTRA_LOCATION_DB_ID, location.getId().toString());
//        startService(mServicePostIntent);
    }

    public void setDrawerIndicator() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void setHomeAsUpIndicator() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void showList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mPointsListFragment)
                .commit();
    }

    public void showMap() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, mPointsMapFragment)
                .commit();
    }

    private void navigate(final int itemId) {
        switch (itemId) {
            case R.id.drawer_item_1:
                mCurrentLocationType = LocationType.battery;
                break;
            case R.id.drawer_item_2:
                mCurrentLocationType = LocationType.glass;
                break;
            case R.id.drawer_item_3:
                mCurrentLocationType = LocationType.paper;
                break;
            case R.id.drawer_item_4:
                mCurrentLocationType = LocationType.plastic;
                break;
            default:
                // ignore
                break;
        }
        if (null != mLoaderManager) //Restart loader only when it is inited
            mLoaderManager.restartLoader(LOCATION_LOADER_ID, null, mLocationLoaderCallbacks);
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoaderManager = getSupportLoaderManager();
        mLoaderManager.initLoader(LOCATION_LOADER_ID, null, mLocationLoaderCallbacks);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_item_add_location:
                Intent intent = new Intent(this, AddLocationActivity.class);
                startActivity(intent);
                break;
            default:
                //meh
        }
    }

    public enum LocationType {
        battery(CleanCityApplication.getInstance().getString(R.string.item_1)),
        glass(CleanCityApplication.getInstance().getString(R.string.item_2)),
        paper(CleanCityApplication.getInstance().getString(R.string.item_3)),
        plastic(CleanCityApplication.getInstance().getString(R.string.item_4));

        private String readableName;

        LocationType(String readableName) {
            this.readableName = readableName;
        }

        @Override
        public String toString() {
            return readableName;
        }
    }
}