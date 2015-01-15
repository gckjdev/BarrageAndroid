package com.orange.barrage.android.ui.topic;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orange.barrage.android.ui.topic.data.PictureTopicData;
import com.orange.barrage.android.ui.topic.model.PictureTopicItem;
import com.orange.barrage.android.util.ContextManager;

/**
 * Created by Rollin on 2015/1/1.
 */
public class PictureTopicAdapter extends BaseAdapter{

    private PictureTopicData mData;

    public PictureTopicAdapter(){
        super();
        mData = PictureTopicData.getsInstance();
    }
    @Override
    public int getCount() {
        return mData.getTopics().size();
    }

    @Override
    public Object getItem(int position) {
        return mData.getTopics().get(position);
    }

    @Override
    public long getItemId(int position) {
        return  mData.getTopics().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PictureTopicContainer container = null;
        if(convertView== null){
            container = new PictureTopicContainer(ContextManager.getContext());
        }else{
            container = (PictureTopicContainer) convertView;
        }
        PictureTopicItem item = mData.getTopics().get(position);
        return container;
    }
}
