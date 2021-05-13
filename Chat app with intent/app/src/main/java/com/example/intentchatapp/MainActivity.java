package com.example.intentchatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public static final int EXTRA_MASAGE_KEY_TO_AHIHI = 100;
    public static final int PERMISSION_REQUESS = 15;
    public static final int MY_AVATAR_KEY = 1975;
    public static User1 user1 = new User1();
    public static User2 user2 = new User2();
    DeviceOrShoot ahihi = new DeviceOrShoot();
    public static final int REQUEST_IMAGE_CAPTURE = 1969;
    public static final int PERMISSION_CAPTURE = 2001;
    public static final int RESULT_LOAD_IMAGE = 2000;
    public static String m,n;
    public static Bitmap imageBitmap;

    Button button;
    EditText username;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.User2Name);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);


    }

    // Hàm bắt đầu vào khung chat
    public void StartOnClicked(View view) {
         m = username.getText().toString();
         n = phoneNumber.getText().toString();
        if (m.isEmpty() || n.isEmpty())
            Toast.makeText(this,"Text field not null!", Toast.LENGTH_SHORT).show();
        else if ( !isInteger(n))
            Toast.makeText(this,"Not a phone number", Toast.LENGTH_SHORT).show();
        else StartChat();

    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    //Chuyển vào activity chat
    public void StartChat()
    {
        Intent sent = new Intent(this, user1.getClass());
        startActivityForResult(sent, EXTRA_MASAGE_KEY_TO_AHIHI);
    }
    // Hàm đổi avt, gọi intent để chọn cách đổi từ camera hoặc gallery
    public void ChangeAVTOnclick(View view) {
        Intent avatar = new Intent(this, ahihi.getClass());
        startActivityForResult(avatar, MY_AVATAR_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_AVATAR_KEY) { // Nhận result cho việc chọn cách đổi avt
            if (resultCode == DeviceOrShoot.RESULT_OK) {
                String reply = data.getStringExtra(DeviceOrShoot.MY_REPLY);
                if (reply.equals("camera"))
                    UseCamera();
                else UseGallery();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {  // Nhận result cho cách đổi avt bằng camera
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            ImageView imgView = (ImageView) findViewById(R.id.avatar);
            imgView.setImageBitmap(imageBitmap);

        } else if (requestCode == RESULT_LOAD_IMAGE) {  // Nhận result cho cách đổi avt bằng gallery
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.avatar);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageBitmap = BitmapFactory.decodeFile(picturePath);


            }

        }
    }
    // Hàm đổi avt bằng gallery
    private void UseGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MainActivity.PERMISSION_REQUESS);
        } else {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }
    // Hàm đổi avt sử dụng camera
    private void UseCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAPTURE);
        }
        takePictureIntent();
    }
    // Intent chụp ảnh camera
    private void takePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAPTURE)          // Dành cho chụp ảnh
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.CAMERA)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        takePictureIntent();
                    }
                }
            }
        if (requestCode == PERMISSION_REQUESS)      // Dành cho lấy từ gallery
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Intent ii = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(ii, RESULT_LOAD_IMAGE);
                    } else Toast.makeText(this, "Permissinog denied...", Toast.LENGTH_SHORT).show();
                }
            }


    }
    // Hàm này để ngăn sự click từ những component khác trỏ vào sự kiện start.
    public void OnclickChoVui(View view)
    {

    }
}