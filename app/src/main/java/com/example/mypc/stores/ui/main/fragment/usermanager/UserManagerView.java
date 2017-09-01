package com.example.mypc.stores.ui.main.fragment.usermanager;

import com.example.mypc.stores.data.model.Location;

/**
 * Created by congp on 8/28/2017.
 */

public interface UserManagerView {
    void onLoadLocationSuccess(Location location);
    void onRequestFailure(String msg);
}
