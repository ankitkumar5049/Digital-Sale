package com.example.digitalsale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitalsale.model.CreateTransaction;
import com.example.digitalsale.model.FinalValue;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ArrayList<CreateTransaction> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPurchase = findViewById(R.id.btnPurchase);
        Button btnSale = findViewById(R.id.btnSale);
        TextView purchase = findViewById(R.id.txtTotalPurchase);
        TextView sale = findViewById(R.id.txtTotalSale);

        ArrayList<FinalValue> list = new ArrayList<>();

        api();


        btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FrameActivity.class);
                intent.putExtra("buttonSale",R.id.btnSale);
                startActivity(intent);
            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FrameActivity.class);
                intent.putExtra("buttonPurchase",R.id.btnPurchase);
                startActivity(intent);

            }
        });



    }

    public void api() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://cf9d-122-168-199-16.ngrok.io//api/getTransactionList";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("response");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);

                                CreateTransaction transaction = new CreateTransaction();
                                transaction.date = jo.getString("date");
                                transaction.time = jo.getString("time");
                                transaction.name = jo.getString("name");
                                transaction.pricePerItem = jo.getInt("pricePerItem");
                                transaction.unit = jo.getString("unit");
                                transaction.quantity = jo.getInt("quantity");
                                transaction.totalPrice = jo.getInt("totalPrice");
                                transaction.typeOfTransaction = jo.getString("typeOfTransaction");

                                list.add(transaction);

                                recyclerAdapter = new RecyclerAdapter(list);
                                recyclerView.setAdapter(recyclerAdapter);
                                recyclerView.setLayoutManager(layoutManager);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }




}
