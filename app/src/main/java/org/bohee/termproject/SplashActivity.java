package org.bohee.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;


public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("state", "launch");
        startActivity(intent);
        finish();
    }
}
