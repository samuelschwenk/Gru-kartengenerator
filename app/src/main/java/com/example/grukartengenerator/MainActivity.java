package com.example.grukartengenerator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button continueButton;
    File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "foto.jpg");
    RadioGroup maingroup;
    RadioButton sonnig;
    RadioButton regnerisch;
    RadioButton frostig;
    RadioButton bewoelkte;
    EditText name;
    EditText ort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maingroup = findViewById(R.id.maingroup);
        sonnig = findViewById(R.id.sonnige);
        regnerisch = findViewById(R.id.regnerische);
        frostig = findViewById(R.id.frostige);
        bewoelkte = findViewById(R.id.bewoelkte);
        continueButton = findViewById(R.id.continueButton);
        name = findViewById(R.id.name);
        ort = findViewById(R.id.ort);

        if (Build.VERSION.SDK_INT>= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
            continueButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(getWeatherstring(sonnig,regnerisch,frostig,bewoelkte,maingroup)!= null) {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                    startActivityForResult(takePictureIntent, 1);
                    }
                }
            });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("permissions", "p1 "+ grantResults[0] + " p2 " + grantResults[1]);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

        }
        else{
            finish();
            System.exit(0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{imageFile.toString()}, null, null);
        Intent contentIntent = new Intent(getApplicationContext(), Result.class);
        contentIntent.putExtra("wetter",getWeatherstring(sonnig,regnerisch,frostig,bewoelkte,maingroup));
        contentIntent.putExtra("name",name.getText().toString());
        contentIntent.putExtra("ort",ort.getText().toString());
        startActivity(contentIntent);
    }
public String getWeatherstring(RadioButton sonnig, RadioButton regnerisch, RadioButton frostig, RadioButton bewoelkte, RadioGroup maingroup){
        this.sonnig = sonnig;
        this.regnerisch = regnerisch;
        this.frostig = frostig;
        this.bewoelkte = bewoelkte;
        this.maingroup = maingroup;
        String Ausgabestring;
        if(maingroup.getCheckedRadioButtonId() == sonnig.getId()){
            Ausgabestring = "sonnigen";
            return  Ausgabestring;
        }
    if(maingroup.getCheckedRadioButtonId() == regnerisch.getId()){
        Ausgabestring = "regnerischen";
        return  Ausgabestring;
    }
    if(maingroup.getCheckedRadioButtonId() == frostig.getId()){
        Ausgabestring = "frostigen";
        return  Ausgabestring;
    }
    if(maingroup.getCheckedRadioButtonId() == bewoelkte.getId()){
        Ausgabestring = "bewölkten";
        return  Ausgabestring;
    }
    return "";
}
}
