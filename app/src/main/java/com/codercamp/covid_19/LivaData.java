package com.codercamp.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LivaData extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;
    ProgressDialog progressDialog;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liva_data);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading.....");
        progressDialog.setCanceledOnTouchOutside(false);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClient webViewClient = new WebViewClient();
        webView.setWebViewClient(webViewClient);
        //progressDialog.show();
        webView.loadUrl("https://virusncov.com/");
       // swipe();
        //progressDialog.dismiss();

    }


    public  void  swipe(){
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                webView.loadUrl("https://virusncov.com/");
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
