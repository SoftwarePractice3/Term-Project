package org.bohee.termproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private MyAdapter adapter;
    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textView);
        listView=(ListView)findViewById(R.id.listView);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        updateTime();
        setData();

        // If list view item is clicked, this function is called
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyItems item=(MyItems)adapterView.getItemAtPosition(i);
                String storeName=item.getStoreName();
                Intent intent=new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
                /*AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                b.setTitle(storeName);
                printInfo(b, i);
                b.setPositiveButton("확인", null);
                b.show();*/
            }
        });


    }

    // set store information
    private void setData(){
        adapter=new MyAdapter();

        adapter.addItem("본찌돈까스", "일식", 1, 1);
        adapter.addItem("알촌", "한식", 2, 3);
        adapter.addItem("예국향", "일식", 4, 5);
        adapter.addItem("밀플랜비", "양식", 6, 7);
        adapter.addItem("돈냉면", "경양식", 8, 9);

        listView.setAdapter(adapter);
    }

    // refresh data of restaurants
    @Override
    public void onRefresh() {
        updateTime();
        swipeRefreshLayout.setRefreshing(false);
    }

    // update to current time
    private void updateTime(){
        long now=System.currentTimeMillis();
        Date date=new Date(now);
        SimpleDateFormat sdf=new SimpleDateFormat("HH-mm-ss");
        String time=sdf.format(date);
        textView.setText("업데이트 시간: "+time);
    }

    // print information of each restaurant
    private void printInfo(AlertDialog.Builder builder, int i){
        builder.setMessage("지도\n");
    }
}
