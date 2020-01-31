package com.example.grukartengenerator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;

public class Result extends AppCompatActivity {
    ImageView imageView;
    File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "foto.jpg");
    File imageFile2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "grußkarte.jpg");
    Button speichernButton ;
    RadioButton black;
    RadioButton white;
    RadioButton red;
    RadioButton blue;
    RadioButton green;
    RadioGroup group;
    String Wetter;
    String Ort;
    String Name;
    Intent oldIntent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        speichernButton = findViewById(R.id.speichernButton);
        imageView = findViewById(R.id.image);
        black = findViewById(R.id.blackbutton);
        white = findViewById(R.id.whitebutton);
        red = findViewById(R.id.redbutton);
        blue = findViewById(R.id.bluebutton);
        green = findViewById(R.id.greenbutton);
        group = findViewById(R.id.group);
        oldIntent = getIntent();
        Ort = oldIntent.getStringExtra("ort");
        Name = oldIntent.getStringExtra("name");
        Wetter = oldIntent.getStringExtra("wetter");
        setImage(R.color.black);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setImage(getColor(black,white,red,blue,green,group));
            }
        });
        speichernButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produceGrueßkarte();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setImage(int Color){
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.toString());
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color);
        paint.setTextSize(45);
        Paint painttwo = new Paint();
        painttwo.setColor(Color);
        painttwo.setTextSize(75);
        canvas.drawText("Viele Grüße aus dem "+Wetter+" "+Ort, 30,200,paint);
        canvas.drawText("Viele Grüße, "+Name, 30,280,painttwo);
        imageView.setImageBitmap(mutableBitmap);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public int getColor(RadioButton black, RadioButton white, RadioButton red, RadioButton blue, RadioButton green, RadioGroup group){
        this.black = black;
        this.white = white;
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.group = group;
        int color;
        if(group.getCheckedRadioButtonId()== black.getId()){
            color = getColor(R.color.black);
            return color;
        }
        if(group.getCheckedRadioButtonId()== white.getId()){
            color = getColor(R.color.white);
            return color;
        }
        if(group.getCheckedRadioButtonId()== red.getId()){
            color = getColor(R.color.red);
            return color;
        }
        if(group.getCheckedRadioButtonId()== blue.getId()){
            color = getColor(R.color.blue);
            return color;
        }
        if(group.getCheckedRadioButtonId()== green.getId()){
            color = getColor(R.color.green);
            return color;
        }
        return 0;
    }
    public void produceGrueßkarte(){
        imageView.buildDrawingCache();
        Bitmap bmp = imageView.getDrawingCache();

        try{
            FileOutputStream fos = new FileOutputStream(imageFile2);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            scanFile(getBaseContext(), Uri.fromFile(imageFile2));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void scanFile(Context context , Uri imageUri){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);

    }
}
