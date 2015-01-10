package com.orange.barrage.android.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.barrage.android.R;
import com.orange.barrage.android.feed.mission.FeedMission;
import com.orange.barrage.android.feed.mission.FeedMissionCallbackInterface;
import com.orange.barrage.android.util.ContextManager;
import com.orange.barrage.android.util.misc.ToastUtil;
import com.orange.barrage.android.util.persistent.LevelDBTestDAO;
import com.orange.protocol.message.BarrageProtos;

import java.util.List;

/**
 * Created by pipi on 15/1/6.
 */
public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv=(TextView)getActivity().findViewById(R.id.textset);
        tv.setText("Hello World");

        Button button = (Button)getActivity().findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                FeedMission.getInstance().getTimelineFeed(new FeedMissionCallbackInterface() {
                    @Override
                    public void handleSuccess(String id, List<BarrageProtos.PBFeed> list) {

                    }

                    @Override
                    public void handleFailure(int errorCode) {

                    }
                });
            }
        });

        // YOU CAN ADD SOME TEST CODE HERE

        Button dbButton = (Button)getActivity().findViewById(R.id.test_db_button);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelDBTestDAO dao =  LevelDBTestDAO.getInstance();
                dao.saveText("id1", "value1");
                String value = dao.getText("id1");
                Toast.makeText(ContextManager.getContext(),value, Toast.LENGTH_SHORT).show();
            }
        });
    }

}