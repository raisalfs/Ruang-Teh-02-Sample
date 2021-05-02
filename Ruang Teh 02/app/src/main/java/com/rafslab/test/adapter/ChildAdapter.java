package com.rafslab.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rafslab.test.R;
import com.rafslab.test.models.Data;
import com.rafslab.test.models.Type;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Data> dataList;
    private final LayoutInflater inflater;

    public ChildAdapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.child_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = dataList.get(position);
        String price = "Rp." + data.getPrice().replace("k", ".000");
        holder.title.setText(data.getTitle());
        if (data.getPrice().equals("-")) {
            List<Type> typeList = dataList.get(position).getTypeList();
            String priceSmall = typeList.get(0).getValue() + " : " + "Rp." + typeList.get(0).getPrice().replace("k", ".000");
            String priceLarge = typeList.get(1).getValue() + " : " + "Rp." + typeList.get(1).getPrice().replace("k", ".000");
            String total = priceSmall + "\n" + priceLarge;
            holder.price.setText(total);
        } else {
            holder.price.setText(price);
        }
        holder.image.setImageResource(R.drawable.image_sample);
        holder.itemView.setOnClickListener(v-> Toast.makeText(mContext, data.getTitle() + "clicked", Toast.LENGTH_SHORT));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView price;
        private final ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
        }
    }
}
