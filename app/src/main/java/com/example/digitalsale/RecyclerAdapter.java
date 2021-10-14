package com.example.digitalsale;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalsale.model.CreateTransaction;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<CreateTransaction> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateAndTime;
        private final TextView productname;
        private final TextView quantity;
        private final TextView totalPur;
        private final TextView totSal;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            dateAndTime = (TextView) view.findViewById(R.id.txtTimeAndDate);
            productname = (TextView) view.findViewById(R.id.txtProductName);
            quantity = (TextView) view.findViewById(R.id.txtUnitAndQuantity);
            totalPur = (TextView) view.findViewById(R.id.txtTotalPurchase);
            totSal = (TextView) view.findViewById(R.id.txtTotalSale);
        }

    }

    public RecyclerAdapter( ArrayList<CreateTransaction> itemList) {
        localDataSet = itemList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_card_deatail, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        viewHolder.dateAndTime.setText(localDataSet.get(position).date + localDataSet.get(position).time);
        viewHolder.productname.setText(localDataSet.get(position).name);
        viewHolder.quantity.setText(localDataSet.get(position).quantity+"*"+localDataSet.get(position).unit);
        viewHolder.totalPur.setText("00");
        viewHolder.totSal.setText(localDataSet.get(position).totalPrice);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
