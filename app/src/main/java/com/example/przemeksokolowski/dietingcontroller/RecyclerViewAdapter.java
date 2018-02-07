package com.example.przemeksokolowski.dietingcontroller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.przemeksokolowski.dietingcontroller.model.ChoosenProducts;

import java.util.ArrayList;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AdapterViewHolder> {

    private Context mContext;

    RecyclerViewAdapter(@NonNull Context context) {
        mContext = context;
    }

    private ArrayList<ChoosenProducts> mArrayList;


    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false);
        view.setFocusable(true);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        // TODO(1) bindowanie widoków -> w zależności od tego co będzie zwracało API!
    }

    @Override
    public int getItemCount() {
        if (mArrayList == null)
            return 0;
        else
            return mArrayList.size();
    }

    void swapList(ArrayList<ChoosenProducts> newList) {
        mArrayList = newList;
        notifyDataSetChanged();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView nameTextView, caloriesTextView, weightTextView;

        AdapterViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.product_name_tv);
            caloriesTextView = itemView.findViewById(R.id.product_calories_tv);
            weightTextView = itemView.findViewById(R.id.product_weight_tv);
        }
    }
}
