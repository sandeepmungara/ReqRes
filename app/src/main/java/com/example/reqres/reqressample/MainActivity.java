package com.example.reqres.reqressample;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reqres.reqressample.models.Reqres;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by sandeep on 05/04/18.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private String TAG = "MainActivity";
    private CustomRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Reqres reqres;
    private TextView noResultsText;
    private Button addNewUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isNetworkConnected()){
            Toast.makeText(MainActivity.this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            progressBar.setVisibility(View.VISIBLE);
            callWebService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        noResultsText = findViewById(R.id.no_results);
        recyclerView.setHasFixedSize(true);
        addNewUserBtn = findViewById(R.id.add_newuser);

        addNewUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewUser.class));
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reqres = new Reqres();
        adapter = new CustomRecyclerAdapter(reqres.getUsere());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }


    public void callWebService() throws IOException {

        OkHttpClient client = new OkHttpClient();

        String url = "https://reqres.in/api/users";

        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();

        Request request = new Request.Builder()
                .url(httpBuider.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String jsonResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Reqres reqres = new Gson().fromJson(jsonResponse,Reqres.class);
                        if(reqres != null && reqres.getUsere().length > 0){
                            noResultsText.setVisibility(View.GONE);
                            adapter.setUsers(reqres.getUsere());

                        }else{

                            noResultsText.setVisibility(View.VISIBLE);
                            adapter.setUsers(null);
                            Toast.makeText(MainActivity.this,"No search results",Toast.LENGTH_SHORT).show();
                        }

                    }




                });

            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onClick(View view) {

    }
}
