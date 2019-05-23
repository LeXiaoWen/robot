package com.leo.robot.base;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.leo.robot.unity.MyUnityPlayer;

/**
 * created by Leo on 2019/5/7 20 : 57
 */


public class UnityPlayerNativeFragment extends Fragment {
    protected MyUnityPlayer mUnityPlayer;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static UnityPlayerNativeFragment newInstance(int sectionNumber) {
        UnityPlayerNativeFragment fragment = new UnityPlayerNativeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().takeSurface(null);
        getActivity().setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        getActivity().getWindow().setFormat(PixelFormat.RGB_565);
        mUnityPlayer = new MyUnityPlayer(getActivity());
        if (mUnityPlayer.getSettings().getBoolean("hide_status_bar", true))
            getActivity().getWindow().setFlags
                    (WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int glesMode = mUnityPlayer.getSettings().getInt("gles_mode", 1);
        boolean trueColor8888 = false;
        mUnityPlayer.init(glesMode, trueColor8888);
        mUnityPlayer.windowFocusChanged(true);
        View playerView = mUnityPlayer.getView();
        return playerView;
    }

//    @Override
//    public void onDestroy() {
//        mUnityPlayer.quit();
//        super.onDestroy();
//    }

    @Override
    public void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

}
