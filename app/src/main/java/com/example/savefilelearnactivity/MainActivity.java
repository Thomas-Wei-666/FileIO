package com.example.savefilelearnactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private FileOutputStream fileOutputStream = null;

    private BufferedWriter writer = null;

    private OutputStreamWriter outputStreamWriter = null;

    private String text;

    @Override
    protected void onDestroy() {
        super.onDestroy();
            saveText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.et_input_text);
        Button button = (Button) findViewById(R.id.bt_save);

        text = editText.getText().toString();

        button.setOnClickListener(v -> {
                saveText(text);
        });


    }

    private void saveText(String text) {
        try {
        fileOutputStream = openFileOutput("myfile.txt", MODE_PRIVATE);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        writer = new BufferedWriter(outputStreamWriter);
        writer.write(text);
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
            Log.i("write","success");
        }
    }
}