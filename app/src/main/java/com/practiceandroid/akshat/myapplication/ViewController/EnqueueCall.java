package com.practiceandroid.akshat.myapplication.ViewController;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.practiceandroid.akshat.myapplication.model.News;
import com.practiceandroid.akshat.myapplication.util.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akshat-3049 on 29/05/18.
 */

public class EnqueueCall {

    ViewControl viewControl;
    ProgressDialog progressDialog;
    Context context;

    public EnqueueCall(ViewControl viewControl, Context context) {
        this.viewControl = viewControl;
        this.progressDialog = new ProgressDialog(context);
        this.context = context;
        initProgressDialog();
    }

    private void initProgressDialog() {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading News...please wait");
    }

     void enqueueCall(Call<News> call, final boolean isContinuedRequest) {
        progressDialog.show();
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(NetworkUtil.isNetworkAvailable(context)) {

                    try {
                        viewControl.updateRecyclerView(response.body().getArticles(), isContinuedRequest);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                    } catch (NullPointerException e) {
                        Toast.makeText(context, "Something went Wrong...Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("FAILURE",t.toString());
            }
        });
    }

}
