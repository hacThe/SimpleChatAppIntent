package com.example.intentchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DeviceOrShoot extends AppCompatActivity {

    public  static  final String MY_REPLY = "12";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_or_shoot);
    }

    // Hàm trả về string camera nếu click vào button camera
    public void Camera(View view)
    {
        String rp = "camera";
        Intent reply = new Intent();
        reply.putExtra(MY_REPLY,rp);
        setResult(RESULT_OK,reply);
        finish();
    }
    // Hàm trả về string gallery nếu click vào button gallery
    public void Gallery(View view)
    {
        String rp = "gallery";
        Intent reply = new Intent();
        reply.putExtra(MY_REPLY,rp);
        setResult(RESULT_OK,reply);
        finish();
    }
}