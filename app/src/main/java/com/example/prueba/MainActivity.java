package com.example.prueba;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;



public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        //Load the site in the URL
        String url = "https://dev.recircular.net/";
        //enables Javascript
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient());

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get the FCM registration token
                    String token = task.getResult();

                //set cookie with the token
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setCookie(url, "dev_recircular_token=" + token);
                Toast.makeText(MainActivity.this, cookieManager.getCookie(url), Toast.LENGTH_SHORT).show();

                // Log and toast
                String msg = token;
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                postData(token);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    private void postData(String token) {

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dev.recircular.net/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();

        // below line is to create an instance for our retrofit api class.
        APIToken apitoken = retrofit.create(APIToken.class);

        // calling a method to create a post and passing our modal class.
        Call<APIRespuesta> call = apitoken.createPost(token);

        // on below line we are executing our method.
        call.enqueue(new Callback<APIRespuesta>() {
            @Override
            public void onResponse(Call<APIRespuesta> call, Response<APIRespuesta> response) {
                // this method is called when we get response from our api.
                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // we are getting response from our body
                // and passing it to our modal class.
                APIRespuesta responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nStatus : " + responseFromAPI.getStatus() + "\n" + "Response : " + responseFromAPI.getResponse();

                // below line we are setting our
                // string to our text view.
                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<APIRespuesta> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


