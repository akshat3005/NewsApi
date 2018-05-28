package com.practiceandroid.akshat.myapplication.ViewController;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.practiceandroid.akshat.myapplication.model.News;
import com.practiceandroid.akshat.myapplication.remote.RetrofitClass;
import com.practiceandroid.akshat.myapplication.util.MapUtil;

import retrofit2.Call;

/**
 * Created by akshat-3049 on 29/05/18.
 */

public class SearchViewController implements MaterialSearchView.OnQueryTextListener {

    ViewControl viewControl;

    public SearchViewController(ViewControl viewControl) {
        this.viewControl = viewControl;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        viewControl.onSearchQuery(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
