package com.maikel.blutoothvoip.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maikel.blutoothvoip.R;
import com.maikel.blutoothvoip.bean.BTDevice;

import java.util.List;

/**
 * Created by maikel on 2018/3/25.
 */

public class BluetoothScanAdapter extends RecyclerView.Adapter<BluetoothScanHolder>{
    private Context mContext;
    private List<BTDevice> devices;
    public BluetoothScanAdapter(Context context,List<BTDevice> devices){
        mContext = context;
        this.devices = devices;
    }
    @Override
    public BluetoothScanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_bluetooth_scan,parent,false);
        BluetoothScanHolder holder = new BluetoothScanHolder(item);
        return holder;
    }

    @Override
    public void onBindViewHolder(BluetoothScanHolder holder, int position) {
        BTDevice device = devices.get(position);
        holder.bt_name_tv.setText(device.getBtName());
        holder.bt_address_tv.setText(device.getBtAddress());
    }

    @Override
    public int getItemCount() {
        return devices==null?0:devices.size();
    }
}
