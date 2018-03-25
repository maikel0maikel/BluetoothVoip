package com.maikel.blutoothvoip.setting;

import com.maikel.blutoothvoip.BasePresenter;
import com.maikel.blutoothvoip.BaseView;

/**
 * Created by maikel on 2018/3/25.
 */

public interface ISettingController {

    public interface SettingView extends BaseView{

    }

    public interface SettingController extends BasePresenter{

        void start();



    }

}
