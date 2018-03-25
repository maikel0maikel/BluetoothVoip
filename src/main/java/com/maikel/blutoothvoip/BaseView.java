package com.maikel.blutoothvoip;

/**
 * Created by maikel on 2018/3/22.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);
}
