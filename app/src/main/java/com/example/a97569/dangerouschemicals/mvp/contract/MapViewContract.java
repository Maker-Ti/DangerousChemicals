package com.example.a97569.dangerouschemicals.mvp.contract;

import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;
import java.util.Map;

/**
 * Created by 97569 on 2018/11/17.
 */

public interface MapViewContract {
    interface info{
        void showMapViewContract();
        void loadCarManage(String vehVIN);
        void getWarning(String s);
        void setWarningStatus(List<Map<String,String>> dataUnOffer);
    }
   interface view{
       void loadInfoView(List<LatLng> options);
       void CarManage(Map<String,String> map,List<LatLng> latLngs);
       void startLoadTimer();
       void loadMainContent();
       void setWarningStatus(List<Map<String,String>> dataOffer,List<Map<String,String>> dataUnOffer);
       void successToast(String info);

   }

}
