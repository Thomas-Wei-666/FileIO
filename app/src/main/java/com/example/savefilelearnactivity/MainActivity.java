package com.example.savefilelearnactivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {


    private String text;

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final String storageFileName = "mydamnfile.txt";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "you've rejected us", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText editText = (EditText) findViewById(R.id.et_input_text);
        Button buttonSave = (Button) findViewById(R.id.bt_save);
        Button buttonImport = (Button) findViewById(R.id.bt_import);


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }

        buttonSave.setOnClickListener(v -> {
            text = editText.getText().toString();//editText的getText()方法必须放在“监听事件“里面才会返回正常结果，不然一直返回空
            saveText(text);
        });
        buttonImport.setOnClickListener(v -> {
            if(!importText().isEmpty()){
                editText.setText(importText());
                editText.selectAll();
            }
        });
    }


    private void saveText(String text) {
        BufferedWriter writer = null;
        try {
            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput(storageFileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            writer = new BufferedWriter(outputStreamWriter);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String importText() {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        String data = "";
        try {
            fileInputStream = openFileInput(storageFileName);
            inputStreamReader = new InputStreamReader(fileInputStream);
            reader = new BufferedReader(inputStreamReader);
            if ((data = reader.readLine()) != null) {
                builder.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(reader!=null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

}

