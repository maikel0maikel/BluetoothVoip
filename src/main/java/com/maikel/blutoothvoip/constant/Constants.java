package com.maikel.blutoothvoip.constant;

/**
 * Created by maikel on 2018/3/25.
 */

public class Constants {

    private Constants() {
    }

    public static final class BTState {
        public static final int STATE_TURNING_ON = 1;
        public static final int STATE_ON = 2;
        public static final int STATE_CONNECTING = 3;
        public static final int STATE_CONNECTED = 4;
        public static final int STATE_DISCONNECTING = 5;
        public static final int STATE_DISCONNECTED = 6;
        public static final int STATE_TURNING_OFF = 7;
        public static final int STATE_OFF = 8;
        public static final int BOND_BONDING = 9;
        public static final int BOND_BONDED = 10;
        public static final int BOND_NONE = 11;
        public static final int ACTION_DISCOVERY_STARTED = 12;
        public static final int ACTION_DISCOVERY_FINISHED = 13;
    }


}
