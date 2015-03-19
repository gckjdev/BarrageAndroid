package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.mission.FriendMission;
import com.orange.barrage.android.friend.mission.callback.GetFriendListCallback;
import com.orange.barrage.android.util.activity.MessageCenter;
import com.orange.barrage.android.util.view.MyViewPagerTools;
import com.orange.protocol.message.UserProtos;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendHomeFragment extends RoboFragment{

    @InjectView(R.id.friend_list_view)
    ListView mListView;

    @InjectView(R.id.tab_icon)
    LinearLayout mPonitLayout;

    @Inject
    FriendListAdapter mAdapter;

    @Inject
    FriendMission mFriendMission;

    @InjectView(R.id.ViewPager)
    ViewPager mViewPager;

    private MyViewPagerTools mViewPagerTools = new MyViewPagerTools();

    private FriendTabView mFriendTabView = null;



    private View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendHomeFragment newInstance(String param1, String param2) {
        FriendHomeFragment fragment = new FriendHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FriendHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mListView.setAdapter(mAdapter);

        loadFriendList();
        mViewPagerTools = new MyViewPagerTools(getActivity() , mViewPager , mPonitLayout);
        mViewPagerTools.setInitTabpointIcon();
        mFriendTabView = null;
        mViewPager.removeAllViews();

        initTab();



    }



    private void initTab(){

        FriendTabView.Params params = new FriendTabView.Params();

        params.bgColor = getResources().getColor(R.color.b_color_home_page_tab_bg);
        params.borderColor = Color.RED;

        for(int i = 0 ; i < 20 ; i ++){
            params.title = "shaduhasssssss"+i;
            initPager(params);
        }

//        new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                mViewPager.setVisibility(View.VISIBLE);
//            }
//        }.sendEmptyMessage(0);

    }

    private void initPager(FriendTabView.Params params){
        getmFriendTabView();

        if(!mFriendTabView.addView(params , "")){
            mFriendTabView = null;
            initPager(params);
        }
    }

    private FriendTabView getmFriendTabView(){
        if(mFriendTabView != null) return mFriendTabView;

        mFriendTabView = new FriendTabView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mFriendTabView.setLayoutParams(params);
        mViewPagerTools.addView(mFriendTabView);

        return mFriendTabView;
    }


    private void loadFriendList() {
        mFriendMission.syncFriend(new GetFriendListCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserFriendList friendList) {

                if (errorCode == 0){
                    mAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_friend_home, container, false);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }



    public void setTab(){




    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
