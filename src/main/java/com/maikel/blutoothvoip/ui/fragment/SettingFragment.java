package com.maikel.blutoothvoip.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.maikel.blutoothvoip.BasePresenter;
import com.maikel.blutoothvoip.R;
import com.maikel.blutoothvoip.setting.ISettingController;
import com.maikel.blutoothvoip.widget.SwitchButton;

/**
 * Created by maikel on 2018/3/24.
 */

public class SettingFragment extends Fragment implements ISettingController.SettingView{
    private View mRootView;
    private ISettingController.SettingController controller;
    private SwitchButton mSwitchButton;
    private ProgressBar mProgressBar;



    private SwitchButton.OnSwitchChangeListener changeListener = new SwitchButton.OnSwitchChangeListener() {
        @Override
        public void onSwitchChange(String name, boolean switched) {
            if (switched){
                setmProgressBarVisible(View.VISIBLE);
                setmSwitchButtonState(View.GONE);
            }else {
                setmProgressBarVisible(View.GONE);
                setmSwitchButtonState(View.VISIBLE);
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_setting,container,false);
            mSwitchButton = mRootView.findViewById(R.id.connect_device_switch);
            mProgressBar = mRootView.findViewById(R.id.connect_device_progress);
            mSwitchButton.setSwitchChangeListener(changeListener);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent!=null){
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        controller = (ISettingController.SettingController) presenter;
    }

    private void setmProgressBarVisible(int visibility){
        mProgressBar.setVisibility(visibility);
    }

    private void setmSwitchButtonState(int visibility){
        mSwitchButton.setVisibility(visibility);
    }
}
