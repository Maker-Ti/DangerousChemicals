package com.example.a97569.dangerouschemicals.mvp.contract;

/**
 * Created by Maker on 2018/12/14.
 */

public interface WarningContract {
    interface info{
        void loginConnect(String username,String password);
    }
    interface view{
        void loginSuccess(String vehVIN,String plateNumber);
        void loginFalt();
    }
}
