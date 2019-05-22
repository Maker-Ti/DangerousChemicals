package com.example.a97569.dangerouschemicals.mvp.contract;

import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by 97569 on 2018/12/1.
 */

public interface History_ViewPagerContract {

    interface info{
        void showhistory(String string);
        void getWarinig(String plateNumber);
        void test();
        void offerWarning(List<Map<String,String>> data1);
    }

    interface view{
        void showhistorymanage(List<String> liquideveltime,List<String> tempreaturetime, List<String> pressuretime,
                               List<String> liquidevel, List<String> tempreature, List<String> pressure);
        void checkdata(List<String> liquideveltime,List<String> tempreaturetime, List<String> pressuretime,
                       List<String> liquidevel, List<String> tempreature, List<String> pressure);

        void referFault();
       void setWarningList(List<Map<String,String>> data1,List<Map<String,String>> data0);
        void viewtest();
        void finishOffer(String info);
    }
}
