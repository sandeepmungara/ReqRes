package com.example.reqres.reqressample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reqres.reqressample.models.Reqres;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * Created by sandeep on 05/04/18.
 */
public class NewUser extends AppCompatActivity {

    private EditText nameTv, jobTv;
    private TextView errorMsgTv, headerTextView;
    private Button addUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        init();
    }

    private void init() {
        nameTv = (EditText) findViewById(R.id.name);
        jobTv = (EditText) findViewById(R.id.job);
        headerTextView = (TextView) findViewById(R.id.header);
        errorMsgTv = (TextView)findViewById(R.id.errormsg);
        addUserBtn = (Button)findViewById(R.id.add);
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        String name, job;
        name = nameTv.getText().toString().trim();
        job = jobTv.getText().toString().trim();

        if(!name.equalsIgnoreCase("") && !job.equalsIgnoreCase("")){
            sendRequest(name , job);
        }else {
            errorMsgTv.setVisibility(View.VISIBLE);
            nameTv.setText("");
            jobTv.setText("");
        }
    }

    private void sendRequest(String name, String job) {
        if(!isNetworkConnected()){
            Toast.makeText(NewUser.this,"Please check your internet connection",Toast.LENGTH_SHORT).show();

        }else{
            try {
                callWebService(name , job);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void callWebService(String name, String job) throws IOException {

        OkHttpClient client = new OkHttpClient();

        String url = "https://reqres.in/api/users";

//        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
//        httpBuider.addQueryParameter("name", name);
//        httpBuider.addQueryParameter("job", job);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("job", job)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String jsonResponse = response.body().string();

                NewUser.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(response.code() == 201){
                            headerTextView.setText("Response Code : "+ response.code() + "      "+
                                    "Response : " +jsonResponse);
                            nameTv.setVisibility(View.GONE);
                            jobTv.setVisibility(View.GONE);
                            addUserBtn.setVisibility(View.GONE);
                        }else{
                            Toast.makeText(NewUser.this, "New User was not Added"+jsonResponse, Toast.LENGTH_SHORT).show();
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
}
