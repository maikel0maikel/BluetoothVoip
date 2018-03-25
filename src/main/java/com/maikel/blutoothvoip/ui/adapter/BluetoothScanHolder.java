package com.maikel.blutoothvoip.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.maikel.blutoothvoip.R;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothScanHolder extends RecyclerView.ViewHolder{

    public TextView bt_name_tv;
    public TextView bt_address_tv;
    public BluetoothScanHolder(View itemView) {
        super(itemView);
        bt_name_tv = itemView.findViewById(R.id.bt_name_tv);
        bt_address_tv = itemView.findViewById(R.id.bt_address_tv);
    }
}
