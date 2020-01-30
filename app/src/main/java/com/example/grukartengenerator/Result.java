package com.example.grukartengenerator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;

public class Result extends AppCompatActivity {
    ImageView imageView;
    File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "foto.jpg");
    File imageFile2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "grußkarte.jpg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        imageView = findViewById(R.id.image);
        setImage();
    }
    public void setImage(){
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.toString());
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("Testtext", 10,200,paint);
        imageView.setImageBitmap(mutableBitmap);
    }

}
