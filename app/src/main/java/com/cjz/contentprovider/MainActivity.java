package com.cjz.contentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cjz.contentprovider.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private List<SmsInfo> smsInfos;
    public List<SmsInfo> getSmsInfos(){
        return smsInfos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 1);
                if (mainFragment!=null){
                    replace(mainFragment);
                }else {
                    mainFragment = new MainFragment();
                    replace(mainFragment);
                }
                mButton.setVisibility(View.GONE);
            }
        });
    }
    private MainFragment mainFragment;

    public void initView() {
        mButton = findViewById(R.id.button);
        smsInfos = new ArrayList<SmsInfo>();
    }

    public void getSms() {
        Uri uri = Uri.parse("content://sms/");//获取系统信息的uri
        ContentResolver resolver = getContentResolver();//获取ContentResolver
        Cursor cursor = resolver.query(uri, new String[]{"_id", "address", "body"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (smsInfos != null) {
                smsInfos.clear();
            }
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String address = cursor.getString(1);
                String body = cursor.getString(2);
                SmsInfo smsInfo = new SmsInfo(id, address, body);
                smsInfos.add(smsInfo);
            }
            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getSms();
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败，不能读取系统短信", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void replace(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mButton.setVisibility(View.VISIBLE);
    }
}
