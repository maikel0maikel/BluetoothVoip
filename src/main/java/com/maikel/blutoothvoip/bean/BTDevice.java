package com.maikel.blutoothvoip.bean;

/**
 * Created by maikel on 2018/3/25.
 */

public class BTDevice {
    private String btName;
    private String btAddress;

    public BTDevice(){

    }
    public BTDevice(String name,String address){
        btName = name;
        btAddress = address;
    }

    public String getBtName() {
        return btName;
    }

    public void setBtName(String btName) {
        this.btName = btName;
    }

    public String getBtAddress() {
        return btAddress;
    }

    public void setBtAddress(String btAddress) {
        this.btAddress = btAddress;
    }
}
