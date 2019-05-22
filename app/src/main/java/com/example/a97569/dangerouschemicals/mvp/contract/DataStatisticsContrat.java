package com.example.a97569.dangerouschemicals.mvp.contract;

import java.util.List;

/**
 * Created by 97569 on 2018/12/20.
 */

public interface DataStatisticsContrat {
    interface info{
        void showmanage(String s);
        void showsubmitted(String s);
    }

    interface view{
        void showdatamanage(List<String> data, List<String> down);
        void showdatasubmitted(List<String> number, List<String> strings);
    }
}
