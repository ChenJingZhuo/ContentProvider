package com.cjz.contentprovider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cjz.contentprovider.MainActivity;
import com.cjz.contentprovider.R;
import com.cjz.contentprovider.SmsInfo;

import java.util.List;

public class AppAdapter extends BaseAdapter {

    private MainActivity main;
    private List<SmsInfo> smsInfos;

    public AppAdapter(MainActivity main) {
        this.main = main;
    }

    @Override
    public int getCount() {
        smsInfos = main.getSmsInfos();
        return smsInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return smsInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmsInfo smsInfo = smsInfos.get(position);
        ViewHold hold;
        if (convertView == null) {
            convertView = LayoutInflater.from(main).inflate(R.layout.main_item, null);
            hold = new ViewHold();
            hold.textView = convertView.findViewById(R.id.tv_sms);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }
        sb = new StringBuffer();
        sb.append("手机号码：" + smsInfo.getAddress() + "\n");
        sb.append("短信内容：" + smsInfo.getBody());
        hold.textView.setText(sb.toString());
        return convertView;
    }
    private StringBuffer sb;

    class ViewHold {
        TextView textView;
    }

}
