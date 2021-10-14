package com.example.digitalsale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class FrameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        if(getIntent().hasExtra("buttonSale")){
            getSupportFragmentManager().beginTransaction().add(R.id.frame,new SellFragment()).commit();
        }
        else if(getIntent().hasExtra("buttonPurchase")){
            getSupportFragmentManager().beginTransaction().add(R.id.frame,new PurchaseFragment()).commit();
        }
    }
}