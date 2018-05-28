package com.practiceandroid.akshat.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.practiceandroid.akshat.myapplication.ViewController.ViewControl;
import com.practiceandroid.akshat.myapplication.adapters.NewsAdapter;
import com.practiceandroid.akshat.myapplication.adapters.RecyclerViewItemTouchHelper;
import com.practiceandroid.akshat.myapplication.model.NewsDetails;
import com.practiceandroid.akshat.myapplication.util.MapUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewItemTouchHelper.RecyclerItemTouchHelperListener {

    List<NewsDetails> newsDetailsList = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    MaterialSearchView searchView;
    Toolbar toolbar;
    BottomSheetDialog filterOptions;
    Calendar calendar;
    String fromDate, toDate;
    ViewControl viewControl;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawerLayout(toolbar);
        initBottomSheetDialog();

        coordinatorLayout = findViewById(R.id.coordinator_layout);

        //Setting Recycler View
        recyclerView = findViewById(R.id.news_recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Setting Adapter
        newsAdapter = new NewsAdapter(getApplicationContext(), (ArrayList<NewsDetails>) newsDetailsList);
        recyclerView.setAdapter(newsAdapter);

        //Setting Swipe Fucntion
        initItemTouchHelper();

        //Will Decide what is shown on the views
        viewControl = new ViewControl(this, newsDetailsList, newsAdapter);

        //Initialize Search Bar
        initSearchBar();

        //Initial Title
        toolbar.setTitle("Latest News");

        //Initial List
        viewControl.enqueueTopNewsCall(new MapUtil("", "", "in", "", "", "").getQueryMap(), false);

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        viewControl.onSwipe(viewHolder, direction, position, coordinatorLayout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_filter) {
            filterOptions.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        viewControl.getCategoryNews(item.getTitle().toString());
        if(item.getTitle().toString().equalsIgnoreCase("general")) {
            toolbar.setTitle("Latest News");
        }
        else {
            toolbar.setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initSearchBar() {
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(viewControl.getSearchViewQueryListener());

    }

    private void initItemTouchHelper() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackLeft = new RecyclerViewItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallbackLeft).attachToRecyclerView(recyclerView);
    }

    private void initDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initBottomSheetDialog() {
        filterOptions = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.filter_options, null);
        filterOptions.setContentView(sheetView);

        LinearLayout date = filterOptions.findViewById(R.id.date_filter);
        LinearLayout sort = filterOptions.findViewById(R.id.sort);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewControl.enqueueDateCall(false);
                filterOptions.dismiss();
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterOptions.dismiss();

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final String array[] = new String[3];
                array[0] = "Relevancy";
                array[1] = "Popularity";
                array[2] = "Date";

                builder.setTitle("Sort By")
                        .setItems(array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                viewControl.sortList(array[which].toLowerCase().equals("date") ? "publishedAt" : array[which].toLowerCase(), false);
                            }
                        });

                builder.show();
            }
        });

    }


}
