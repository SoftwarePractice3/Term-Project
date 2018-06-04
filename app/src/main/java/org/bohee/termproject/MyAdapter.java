package org.bohee.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bohee on 2018-05-06.
 */

public class MyAdapter extends BaseAdapter {
    private ArrayList<MyItems> list=new ArrayList<>();

    // 리스트 항목 개수 반환
    @Override
    public int getCount() {
        return list.size();
    }

    // i번째 리스트 항목 반환
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    // i번째 리스트 항목의 인덱스(=i) 반환
    @Override
    public long getItemId(int i) {
        return i;
    }

    // i번째 리스트 항목을 화면에 출력하는 view 반환
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context c=viewGroup.getContext();

        if(view==null){
            LayoutInflater li=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=li.inflate(R.layout.listview, viewGroup, false);
        }

        TextView storeNameText=(TextView)view.findViewById(R.id.name);
        TextView foodTypeText=(TextView)view.findViewById(R.id.type);
        TextView table2Text=(TextView)view.findViewById(R.id.table2);
        TextView table4Text=(TextView)view.findViewById(R.id.table4);

        MyItems item=list.get(i);

        storeNameText.setText(item.getStoreName());
        foodTypeText.setText(item.getFoodType());
        table2Text.setText(item.getTable2());
        table4Text.setText(item.getTable4());

        return view;
    }

    public void addItem(String storeName, String foodType, int table2, int table4){
        MyItems item=new MyItems();

        item.setStoreName(storeName);
        item.setFoodType(foodType);
        item.setTable2(table2);
        item.setTable4(table4);

        list.add(item);
    }
}
