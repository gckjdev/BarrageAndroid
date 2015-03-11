package com.example.listviewtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {
    private ListView lv;
    ArrayList<HashMap<String,Object>> list;
    private LayoutInflater layoutInflater;
    static class ViewHolder
    {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
    }
    private class MyAdapter extends BaseAdapter
    {
        public MyAdapter(Context context)
        {
            layoutInflater=LayoutInflater.from(context);
        }
        public MyAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return getData().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null)
            {
                convertView=layoutInflater.inflate(R.layout.list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageview);
                viewHolder.textView1=(TextView)convertView.findViewById(R.id.textview1);
                viewHolder.textView2=(TextView)convertView.findViewById(R.id.textview2);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.imageView.setBackgroundResource(R.drawable.ic_launcher);
            viewHolder.textView1.setText(getData().get(position).get("textview1").toString());
            viewHolder.textView2.setText(getData().get(position).get("textview2").toString());
            return convertView;
        }
    }

    //填充数据
    private ArrayList<HashMap<String,Object>> getData()
    {
        ArrayList<HashMap<String,Object>> list1=new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<30;i++)
        {
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("image",R.drawable.ic_launcher);
            map.put("textview1","第"+i+"个文字");
            map.put("textview2","第"+i+"右边文字");
            list1.add(map);
        }
        return list1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(new MyAdapter(MainActivity.this));
    }
}
