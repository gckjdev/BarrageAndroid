package com.orange.barrage.android.friend.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.orange.barrage.android.R;
import com.orange.protocol.message.UserProtos;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import roboguice.util.Ln;

/**
 * Created by Administrator on 2015/3/26.
 */
public class FriendListInfoNewListView extends ListView {


    Vector<Object> mSelectVector = new Vector<>();

    List mObjs;

    MyAdapter mMyAdapter;

    List<Map<String , Object>> mData;

    int mSelectPostion;

    String mFrom[] = null;
    int mTo[] = null;

    public static final int SELECT_IS = R.drawable.x_friendlist_select;

    public static final int SELECT_NOT = R.drawable.x_comments_white;

    public FriendListInfoNewListView(Context context) {
        super(context);
    }

    public FriendListInfoNewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FriendListInfoNewListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FriendListInfoNewListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    /**
     *  @param data
     * @param layoutId
     * @param from
     * @param to
     * @param objs
     * @param selectpotion  显示选择的ImageView，实在一行布局的位置
     */
    public void setData(List<Map<String , Object>> data , int layoutId , String []from , int[] to , List objs , int selectpotion){
        mData = data;
        mSelectPostion = selectpotion;
        mFrom = from;
        mTo = to;
        mObjs = objs;

        mMyAdapter = new MyAdapter(getContext(),data,layoutId , from , to);
        setAdapter(mMyAdapter);
    }


    public Vector<Object> getSelectObject(){
        return mSelectVector;
    }

    public void setBindView(SimpleAdapter.ViewBinder viewBinder){
        mMyAdapter.setViewBinder(viewBinder);
    }

    class MyAdapter extends SimpleAdapter{

        /**
         * Constructor
         *
         * @param context  The context where the View associated with this SimpleAdapter is running
         * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
         *                 Maps contain the data for each row, and should include all the entries specified in
         *                 "from"
         * @param resource Resource identifier of a view layout that defines the views for this list
         *                 item. The layout file should include at least those named views defined in "to"
         * @param from     A list of column names that will be added to the Map associated with each
         *                 item.
         * @param to       The views that should display column in the "from" parameter. These should all be
         *                 TextViews. The first N views in this list are given the values of the first N columns
         */
        public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v ;
            if(convertView == null)
                v = super.getView(position, convertView, parent);
            else v = convertView;

            v.setOnClickListener(new OnClickListener() {

                private int location = position;

                @Override
                public void onClick(View v) {
                    Map<String , Object> maps = mData.get(location);
                    Object obj = maps.get(mFrom[mSelectPostion]);
                    if(!(obj instanceof  Integer))
                          throw new NumberFormatException("Invalid int: \"" + obj.toString() + "\"");

                    int resource = (int) obj;
                    Ln.e("you:"+resource);
                    Ln.e(""+(resource == SELECT_IS));
                    if(resource == SELECT_IS){
                        //设置成为不选择
                        resource = SELECT_NOT;
                        mSelectVector.remove(mObjs.get(location));
                    }else{
                        //设置成为选择
                        resource = SELECT_IS;
                        mSelectVector.add(mObjs.get(location));
                    }

                    maps.put(mFrom[mSelectPostion] , resource);
                    ImageView imageView = (ImageView) v.findViewById(mTo[mSelectPostion]);
                    imageView.setImageResource(resource);
                }
            });

            return super.getView(position, v, parent);
        }
    }

}
