package com.cookandroid.dakku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import sticker.DrawableSticker;
import sticker.StickerView;
import sticker.TextSticker;

public class DecoActivity extends AppCompatActivity implements SendEventListener {

    StickerView stickerView;
    Button backPageBtn, saveBtn, addStickerBtn, addDrawingBtn, addTxtBtn, addImgBtn;
    String editedText;
    int txtColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deco);

        stickerView = (StickerView) findViewById(R.id.stickerView);
        backPageBtn = (Button) findViewById(R.id.btnBackPage);
        saveBtn = (Button) findViewById(R.id.btnSave);
        addStickerBtn = (Button) findViewById(R.id.btnAddSticker);
        addDrawingBtn = (Button) findViewById(R.id.btnAddDrawing);
        addTxtBtn = (Button) findViewById(R.id.btnAddTxt);
        addImgBtn = (Button) findViewById(R.id.btnAddImg);


        addStickerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadSticker();
            }
        });

        addImgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startDefaultGalleryApp();
            }
        });
        addTxtBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                EditTextFragment fragment = new EditTextFragment();
                transaction.replace(R.id.frame, fragment);
                transaction.commit();
            }
        });
    }

    void loadSticker(){
        Drawable drawable = getDrawable(R.drawable.muha0);
        DrawableSticker drawableSticker = new DrawableSticker(drawable);
        stickerView.addSticker(drawableSticker);
        Integer stickerCnt = stickerView.getStickerCount();
        Log.d(this.getClass().getName(),Integer.toString(stickerCnt));
    }

    void startDefaultGalleryApp() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK) {
            Toast.makeText(getApplication(), "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        switch(requestCode) {
            case 1:
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try{
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                    } else {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stickerView.addSticker(new DrawableSticker(new BitmapDrawable(bitmap)));
                Integer stickerCnt = stickerView.getStickerCount();
                Log.d(this.getClass().getName(),Integer.toString(stickerCnt));
        }

    }

    @Override
    public void sendMessage(String message) {
        editedText = message;
        // Add Text Sticker
        TextSticker txtSticker = new TextSticker(getApplicationContext());
        txtSticker.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sticker_transparent_background));
        txtSticker.setTextColor(txtColor);
        txtSticker.setText(editedText);
        txtSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        txtSticker.resizeText();

        stickerView.addSticker(txtSticker);
    }

    @Override
    public void sendColor(int color) {
        txtColor = color;
    }
}