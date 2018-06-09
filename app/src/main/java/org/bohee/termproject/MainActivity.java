package org.bohee.termproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String SERVER_URL = "http://13.125.55.61/getData.php";
    ArrayList<HashMap<String, String>> restaurantList = new ArrayList<HashMap<String, String>>();

    private ListView listView;
    private MyAdapter adapter;
    private TextView textView;
    private ImageButton updateButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateButton =(ImageButton)findViewById(R.id.updateButton);
        textView=(TextView)findViewById(R.id.textView);
        listView=(ListView)findViewById(R.id.listView);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        updateTime();
        getRestaurant(SERVER_URL); // get restaurant list from server
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //Toast.makeText(MainActivity.this,"size in handler:"+ restaurantList.size(), Toast.LENGTH_LONG).show();
                setData();
            }
        }, 500);// 0.005초 정도 딜레이를 준 후 시작

        // If list view item is clicked, this function is called
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyItems item=(MyItems)adapterView.getItemAtPosition(i);
                String storeName=item.getStoreName();
                Intent intent=new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                restaurantList.clear();
                updateTime();
                getRestaurant(SERVER_URL); // get restaurant list from server
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setData();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"좌석 수가 업데이트 되었습니다.", Toast.LENGTH_LONG).show();
                    }
                }, 500);// 0.005초 정도 딜레이를 준 후 시작
            }
        });

    }

    // set store information
    private void setData(){
        adapter=new MyAdapter();
        for(int i = 0; i< restaurantList.size(); i++){
            int table_2 = Integer.parseInt(restaurantList.get(i).get("table_2"));
            int table_4 = Integer.parseInt(restaurantList.get(i).get("table_4"));
            adapter.addItem(restaurantList.get(i).get("name"), restaurantList.get(i).get("type"), table_2, table_4);
        }

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

    private void getRestaurant(String SERVER_URL) {
        DatabaseTask databaseTask = new DatabaseTask(getApplicationContext(), restaurantList);
        databaseTask.execute(SERVER_URL);
    }
}
