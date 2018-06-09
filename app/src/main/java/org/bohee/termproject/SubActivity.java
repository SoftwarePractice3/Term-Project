package org.bohee.termproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class SubActivity extends Activity {
    String storeName;
    String address;
    int storeId;
    TextView textView;
    WebView webView;
    ImageView imageView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        textView=(TextView)findViewById(R.id.textView2);
        webView = (WebView)findViewById(R.id.webView);
        Button mapView = (Button) findViewById(R.id.navermap);

        Intent intent = getIntent();
        storeName = intent.getStringExtra("storeName");
        address = intent.getStringExtra("address");
        storeId = intent.getIntExtra("ithStore",0);

        textView.setText(storeName);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("https://m.map.naver.com/search2/search.nhn?query=" + address + "&sm=hty#/map");

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_START);

        switch (storeId) {
            case 0:
                imageView.setImageResource(R.drawable.mealplanb);
                break;
            case 1:
                imageView.setImageResource(R.drawable.ungsaeng);
                break;
            case 2:
                imageView.setImageResource(R.drawable.bonzi);
                break;
            case 3:
                imageView.setImageResource(R.drawable.momstouch);
                break;
            case 4:
                imageView.setImageResource(R.drawable.naezzim);
                break;
            default:
                break;
        }

        mapView.setOnClickListener(onClickListener);

    }

    Button.OnClickListener onClickListener = new View.OnClickListener(){
        public void onClick(View v){
            Intent mapintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.map.naver.com/search2/search.nhn?query=" + address + "&sm=hty#/map"));
            startActivity(mapintent);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }


}
