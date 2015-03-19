package com.orange.barrage.android.friend.ui;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orange.barrage.android.R;
import com.orange.barrage.android.friend.activity.FriendTabDetailInfoAndCreateAndAlterActivity;
import com.orange.barrage.android.friend.mission.FriendMission;
import com.orange.barrage.android.friend.mission.TagMission;
import com.orange.barrage.android.friend.mission.callback.GetFriendListCallback;
import com.orange.barrage.android.friend.mission.callback.GetTagListCallback;
import com.orange.barrage.android.friend.model.TagManager;
import com.orange.barrage.android.util.activity.ActivityIntent;
import com.orange.barrage.android.util.view.MyViewPagerTools;
import com.orange.protocol.message.UserProtos;

import java.util.List;

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
public class FriendHomeFragment extends RoboFragment implements FriendTagView.OnClickTabIconItemListener {

    @InjectView(R.id.friend_list_view)
    ListView mListView;

    @InjectView(R.id.tab_icon)
    LinearLayout mPonitLayout;

    @Inject
    FriendListAdapter mAdapter;

    @Inject
    FriendMission mFriendMission;

    @Inject
    TagMission mTagMission;

    @Inject
    TagManager mTagManager;

    @InjectView(R.id.ViewPager)
    ViewPager mViewPager;

    private MyViewPagerTools mViewPagerTools = new MyViewPagerTools();

    private FriendTagView mFriendTabView = null;



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

//        initTab();

        syncMyTag();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadLocalTagList();
    }


    private void initTab(){

//        FriendTagView.Params params = new FriendTagView.Params();
//
//        params.bgColor = getResources().getColor(R.color.b_color_home_page_tab_bg);
//        params.borderColor = Color.RED;
//
//
//        params.title = "shad";
//        initPager(params);
//        params.title = "shad";
//        initPager(params);
//        params.title = "shad";
//        initPager(params);

//        for(int i = 0 ; i < 20 ; i ++){
//            params.title = "shaduhasssssss"+i;
//            initPager(params);
//        }

//        new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                mViewPager.setVisibility(View.VISIBLE);
//            }
//        }.sendEmptyMessage(0);

        loadLocalTagList();

    }

    private void loadLocalTagList() {

        UserProtos.PBUserTagList tagList = mTagManager.allTags();
        if (tagList == null){
            return;
        }

        List<UserProtos.PBUserTag> list = tagList.getTagsList();
        if (list == null || list.size() == 0){
            return;
        }

        for(UserProtos.PBUserTag userTag : list){

            FriendTagView.Params params = new FriendTagView.Params();
            params.bgColor = userTag.getColor();
            params.title = userTag.getName();
            initPager(params , userTag.getTid());
        }


    }

    private void syncMyTag() {

        mTagMission.getAllTags(new GetTagListCallback() {
            @Override
            public void handleMessage(int errorCode, UserProtos.PBUserTagList tagList) {
                if (errorCode == 0){
                    // reload tag view
                    loadLocalTagList();
                }
            }
        });

    }

    private void initPager(FriendTagView.Params params , String id){
        getmFriendTabView();

        if(!mFriendTabView.addView(params , id)){
            mFriendTabView = null;
            initPager(params , id);
        }
    }

    private FriendTagView getmFriendTabView(){
        if(mFriendTabView != null) return mFriendTabView;

        mFriendTabView = new FriendTagView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mFriendTabView.setLayoutParams(params);
        mViewPagerTools.addView(mFriendTabView);
        mFriendTabView.setOnclickTabItemListener(this);

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

    @Override
    public void onClickItem(String tagId, View v) {
        //点击标签跳转

        ActivityIntent.startIntent(getActivity() , FriendTabDetailInfoAndCreateAndAlterActivity.class , FriendTabDetailInfoAndCreateAndAlterActivity.TABKEY, tagId);

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
