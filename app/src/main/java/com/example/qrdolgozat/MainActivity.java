package com.example.qrdolgozat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    public Button scan, kiir;
    public TextView text1;
    private FajlbaIras fajlbaIras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(com.google.zxing.integration.android.IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("QR Code Scanning");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        kiir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fajlbaIras.kiiras(text1.getText().toString());
                    Toast.makeText(MainActivity.this, "Sikeres fájlbaírás!", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Sikertelen fájlbaírás!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init()
    {
        scan = findViewById(R.id.scan);
        kiir =findViewById(R.id.kiir);
        text1 = findViewById(R.id.text1);
        fajlbaIras = new FajlbaIras();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Kiléptél a scannelésből!", Toast.LENGTH_SHORT).show();
            } else {
                text1.setText(result.getContents());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
