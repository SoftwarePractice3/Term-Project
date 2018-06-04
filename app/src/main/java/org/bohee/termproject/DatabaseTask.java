package org.bohee.termproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseTask extends AsyncTask<String, Void, String> {
    private static final String TAG = "DatabaseTask";
    private static final String TAG_JSON="restaurant";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String TAG_ADDRESS ="address";
    private static final String TAG_TABLE_2 ="table_2";
    private static final String TAG_TABLE_4 ="table_4";

    private static String mJsonString;
    private ArrayList<HashMap<String, String>> tempArrayList;
    Context context;


    public DatabaseTask(Context context, ArrayList<HashMap<String, String>> rArrayList) {
        this.context = context;
        this.tempArrayList = rArrayList;
    }

    @Override
    protected String doInBackground(String... strings) {
        String serverURL = strings[0];

        try {
            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "response code - " + responseStatusCode);

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }

            bufferedReader.close();


            return sb.toString().trim();
        }catch(Exception e){
            Log.d(TAG, "GetData : Error", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d(TAG, "response  - " + s);
        mJsonString = s;
        getData();
        //Toast.makeText(context,"size in post:"+ mJsonString, Toast.LENGTH_LONG).show();
    }

    private void getData(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);
                String type = item.getString(TAG_TYPE);
                String address = item.getString(TAG_ADDRESS);
                String table_2 = item.getString(TAG_TABLE_2);
                String table_4 = item.getString(TAG_TABLE_4);

                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_TYPE, type);
                hashMap.put(TAG_ADDRESS, address);
                hashMap.put(TAG_TABLE_2, table_2);
                hashMap.put(TAG_TABLE_4, table_4);

                tempArrayList.add(hashMap);
            }

        } catch (JSONException e) {
            Log.d(TAG, "getData : ", e);
        }
    }
}