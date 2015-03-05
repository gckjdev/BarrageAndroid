package com.orange.barrage.android.user.ui.register;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.orange.barrage.android.R;
import com.orange.barrage.android.util.activity.BarrageCommonActivity;

import roboguice.inject.InjectView;

/**
 * Created by youjiannuo on 2015/3/4.
 */
public class MainRegisterActivity extends BarrageCommonActivity {

    @InjectView(R.id.zhuche_other_id)
    LinearLayout mlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState , R.layout.activity_registered, R.string.y_zhuce , -1);
    }

    /**
     * 下拉设置
     *
     */
    public void onClickSF(View v){

        if(v.getTag() == null || v.getTag().equals("c")){
            mlayout.setVisibility(View.VISIBLE);
            v.setTag("O");
            ((ImageButton)v).setImageResource(R.drawable.y_zhuce_shouhui);
        }else{
            mlayout.setVisibility(View.GONE);
            v.setTag("c");
            ((ImageButton)v).setImageResource(R.drawable.y_zhuche_xiala);
        }

    }

}
