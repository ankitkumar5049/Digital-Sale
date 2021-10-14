package com.example.digitalsale;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitalsale.model.CreateTransaction;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txtUnit;
    TextView txtDate;
    TextView txtTime;
    TextView txtProductName;
    TextView txtPrice;
    TextView txtUnitOfPrice;
    TextView txtTotalQuantity;
    TextView txtTotalSum;
    Button btnSave;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SellFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SellFragment newInstance(String param1, String param2) {
        SellFragment fragment = new SellFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sell, container, false);

         txtUnit = view.findViewById(R.id.txtAddUnit);
         txtDate = view.findViewById(R.id.txtDate);
         txtTime = view.findViewById(R.id.txtTime);
         txtProductName = view.findViewById(R.id.txtProductName);
         txtPrice = view.findViewById(R.id.txtPrice);
         txtUnitOfPrice = view.findViewById(R.id.txtUnitOfPrice);
         txtTotalQuantity = view.findViewById(R.id.txtTotQuantity);
         txtTotalSum = view.findViewById(R.id.txtTotalSum);
         btnSave = view.findViewById(R.id.btnSave);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

         txtDate.setText(date);
         txtTime.setText(time);

        txtUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toogleBottomSheet();
                }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = txtProductName.getText().toString();
                int pricePerItem = Integer.parseInt(txtUnitOfPrice.getText().toString());
                String unit = txtUnit.getText().toString();
                int quantity = Integer.parseInt(txtTotalQuantity.getText().toString());
                int totalPrice = Integer.parseInt(txtTotalSum.getText().toString());
                String typeOfTransaction = "IN";


                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "http://3f1a-122-168-199-16.ngrok.io/api/createTransaction";


                JSONObject jsonParams = new JSONObject();
                try {
                    jsonParams.put("date", date);
                    jsonParams.put("time", time);
                    jsonParams.put("name", name);
                    jsonParams.put("pricePerItem", pricePerItem);
                    jsonParams.put("unit", unit);
                    jsonParams.put("quantity", quantity);
                    jsonParams.put("totalPrice", totalPrice);
                    jsonParams.put("typeOfTransaction", typeOfTransaction);



                JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonParams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                           Boolean res = response.getBoolean("response");
                            if (res){
                                Log.i("response","Success");
                            }
                            else {
                                Log.i("response","error");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("error","error");


                    }
                });

                    queue.add(jsonObject);

                } catch (Exception e) {
                    Log.i("error", "error occurred");
                }


            }
        });
        return view;

    }

    public void toogleBottomSheet(){
        View view = getLayoutInflater().inflate(R.layout.fragment_unit_sheet,null);

        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();
        LinearLayout call = view.findViewById(R.id.call);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = v.getId();
                Log.i("id", String.valueOf(id));
               /* int id = v.getId();
                switch (id){
                    case R.id.txtPiece:
                        Log.i("data","Piece");
                        break;
                    case R.id.txtKG:
                        Log.i("data","KG");
                        break;
                    case R.id.txtML:
                        Log.i("data","ML");
                        break;
                }*/
            }
        });

    }

    private void sendWorkPostRequest() {

        try {
            String URL = "";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("email", "abc@abc.com");
            jsonBody.put("password", "");
            jsonBody.put("user_type", "");
            jsonBody.put("company_id", "");
            jsonBody.put("status", "");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }
}
