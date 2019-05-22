package com.example.a97569.dangerouschemicals.mvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a97569.dangerouschemicals.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarningListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,String>> data ;
    private List<Boolean> checkList = new ArrayList<>();
    private boolean control = true;
    private boolean allCheck = false;

    public WarningListAdapter(Context context, List<Map<String,String>> data){
        this.context = context;
        this.data =data;
        for(int i=0;i<data.size();i++){
            checkList.add(false);
        }
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_view,null);
            viewHolder.num = convertView.findViewById(R.id.item);
            viewHolder.checkBox = convertView.findViewById(R.id.check);
            viewHolder.content = convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.num.setText(data.get(position).get("num"));
        viewHolder.content.setText(data.get(position).get("info"));
            viewHolder.checkBox.setChecked(false);
            if(checkList.get(position)){
                viewHolder.checkBox.setChecked(true);
            }
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            if(control == false){
                viewHolder.checkBox.setVisibility(View.GONE);
            }

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkList.set(position,viewHolder.checkBox.isChecked());
                Log.e("listadd",""+checkList.get(position));
            }
        });

        return convertView;
    }
    private class ViewHolder{
        TextView num;
        CheckBox checkBox;
        TextView content;
    }


    public List<Boolean> getCheckList() {
        return checkList;
    }
    public void checkBoxVisible(boolean flag){
        this.control = flag;
    }
    public void changeAllCheck(boolean flag){
        if(flag){
            this.allCheck = true;
            for(int i=0;i<checkList.size();i++){
                checkList.set(i,true);
            }
        }else {
            this.allCheck = false;
            for(int i=0;i<checkList.size();i++){
                checkList.set(i,false);
            }
        }

    }
}
