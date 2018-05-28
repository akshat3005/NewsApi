package com.practiceandroid.akshat.myapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.practiceandroid.akshat.myapplication.adapters.NewsAdapter;
import com.practiceandroid.akshat.myapplication.adapters.RecyclerViewItemTouchHelper;
import com.practiceandroid.akshat.myapplication.model.News;
import com.practiceandroid.akshat.myapplication.model.NewsDetails;
import com.practiceandroid.akshat.myapplication.remote.RetrofitClass;
import com.practiceandroid.akshat.myapplication.util.MapUtil;
import com.practiceandroid.akshat.myapplication.util.NetworkUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewItemTouchHelper.RecyclerItemTouchHelperListener {

    List<NewsDetails> newsDetailsList = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    private CoordinatorLayout coordinatorLayout;
    MaterialSearchView searchView;
    Toolbar toolbar;
    BottomSheetDialog filterOptions;
    Calendar calendar;
    String fromDate, toDate;
    Map<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawerLayout(toolbar);
        initSearchBar();
        initBottomSheetDialog();

        recyclerView = findViewById(R.id.news_recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        initItemTouchHelper();


        map = new MapUtil("", "", "in", "", "", "").getQueryMap();
        Call<News> call = RetrofitClass.getNews(map);
        enqueueCall(call);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof NewsAdapter.NewsViewHolder) {
            String name = newsDetailsList.get(viewHolder.getAdapterPosition()).getTitle();

            final NewsDetails deletedItem = newsDetailsList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            newsAdapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    newsAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
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

        switch (item.getItemId()) {
            case R.id.topnews:
                map = new MapUtil("","","in","","","").getQueryMap();
                map.remove("category");
                break;
            case R.id.business:
                map = new MapUtil("","","in","business","","").getQueryMap();
                break;
                case R.id.health:
                    map = new MapUtil("","","in","health","","").getQueryMap();
                break;
                case R.id.science:
                    map = new MapUtil("","","in","science","","").getQueryMap();
                break;
                case R.id.sports:
                    map = new MapUtil("","","in","sports","","").getQueryMap();
                break;
                case R.id.technology:
                    map = new MapUtil("","","in","technology","","").getQueryMap();
                break;
                case R.id.entertainment:
                    map = new MapUtil("","","in","entertainment","","").getQueryMap();
                break;
                case R.id.general:
                    map = new MapUtil("","","in","general","","").getQueryMap();
                break;
        }

        Call<News> call = RetrofitClass.getNews(map);
        enqueueCall(call);
        toolbar.setTitle(item.getTitle());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initSearchBar() {
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("abc",query);
                if(!query.equals("")) {
                    map = new MapUtil(query, "", "", "", "", "","","100","").getQueryMap();
                    Call<News> call = RetrofitClass.getAllNews(map);
                    enqueueCall(call);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("abc",newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Log.d("abc","shown");

            }

            @Override
            public void onSearchViewClosed() {
                Log.d("abc","closed");

            }
        });
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

                datePickerDialog();
                map.put("from",fromDate);
                map.put("from",toDate);

                Call<News> call = RetrofitClass.getAllNews(map);
                enqueueCall(call);
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
                                map.put("sortBy",array[which].toLowerCase().equals("date") ? "publishedAt" : array[which].toLowerCase());
                                Call<News> call = RetrofitClass.getAllNews(map);
                                enqueueCall(call);
                            }
                        });

                builder.show();
            }
        });

    }

    private void datePickerDialog() {
        calendar = Calendar.getInstance();
        DatePickerDialog from = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fromDate = sdf.format(calendar.getTime());

                DatePickerDialog to = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "MM/dd/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        toDate = sdf.format(calendar.getTime());
                    }

                }, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                to.setTitle("To");
                to.show();

            }

        }, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        from.setTitle("From");
        from.show();

    }

    private void enqueueCall(Call<News> call) {

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(NetworkUtil.isNetworkAvailable(getApplicationContext())) {

                    try {
                        newsDetailsList = response.body().getArticles();
                        if (newsDetailsList.size() == 0) {
                            map = new MapUtil("", "", "in", "", "", "").getQueryMap();
                            Call<News> newCall = RetrofitClass.getNews(map);
                            enqueueCall(newCall);
                        }
                        newsAdapter = new NewsAdapter(getApplicationContext(), newsDetailsList);
                        recyclerView.setAdapter(newsAdapter);


                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(), "Something went Wrong...Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.d("FAILURE",t.toString());
            }
        });
    }

}
