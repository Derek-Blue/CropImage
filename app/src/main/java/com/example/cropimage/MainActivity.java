package com.example.cropimage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

public class MainActivity extends AppCompatActivity {

    ImageView imageview;
    Button button;

    Uri imageUri;

    private static final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview = findViewById(R.id.imageview);
        button = findViewById(R.id.botton);

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageview.getDrawable().getCurrent().getConstantState().equals
                        (getResources().getDrawable(R.drawable.ic_add).getConstantState())){
                    Toast.makeText(MainActivity.this, "尚未選擇圖片",Toast.LENGTH_SHORT).show();
                }else {
                    CropImage.activity(imageUri)
                            .setAspectRatio(1,1)//長寬比
                            .setActivityTitle("選擇裁切")
                            .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)//輸出格式
                            .start(MainActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            imageview.setImageURI(imageUri);
        }

        else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri uri = result.getUri();
            imageview.setImageURI(uri);
        }
    }
}
