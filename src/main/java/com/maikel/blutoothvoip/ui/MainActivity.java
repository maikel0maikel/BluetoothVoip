package com.maikel.blutoothvoip.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.TextView;

import com.maikel.blutoothvoip.R;
import com.maikel.blutoothvoip.ui.adapter.FragmentsAdapter;
import com.maikel.blutoothvoip.ui.fragment.CallRecordFragment;
import com.maikel.blutoothvoip.ui.fragment.ContactFragment;
import com.maikel.blutoothvoip.ui.fragment.DialerFragment;
import com.maikel.blutoothvoip.ui.fragment.MessageFragment;
import com.maikel.blutoothvoip.ui.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Class<?>[] FRAGMENTS = {ContactFragment.class, CallRecordFragment.class, DialerFragment.class, MessageFragment.class, SettingFragment.class};

    private static final int[] TAB_IDS = {R.id.tab_contact, R.id.tab_call_record, R.id.tab_dialer, R.id.tab_message, R.id.tab_setting};
    ViewPager contentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ViewStub tabStub = findViewById(R.id.tabs_stub);
        tabStub.inflate();
        contentPage = findViewById(R.id.content_page);

        List<Fragment> fragments = new ArrayList<>();
        for (Class<?> cls : FRAGMENTS) {
            try {
                Fragment fragment = (Fragment) cls.newInstance();
                fragments.add(fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
                finish();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                finish();
            }
        }
        FragmentsAdapter fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(), fragments);
        contentPage.setAdapter(fragmentsAdapter);
        contentPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initTabs();
        setCurrentPage(2);
    }

    private void initTabs() {
        for (int id : TAB_IDS) {
            findViewById(id).setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        int tabIndex = 0;
        switch (v.getId()) {
            case R.id.tab_contact:
                tabIndex = 0;
                break;
            case R.id.tab_call_record:
                tabIndex = 1;
                break;
            case R.id.tab_dialer:
                tabIndex = 2;
                break;
            case R.id.tab_message:
                tabIndex = 3;
                break;
            case R.id.tab_setting:
                tabIndex = 4;
                break;
            default:
                tabIndex = 0;
                break;
        }
        v.setSelected(true);
        setCurrentPage(tabIndex);
    }

    private void setCurrentPage(int tabIndex) {
        for (int i = 0; i < TAB_IDS.length; i++) {
            findViewById(TAB_IDS[i]).setSelected(tabIndex == i);
        }
        contentPage.setCurrentItem(tabIndex, true);
    }
}
