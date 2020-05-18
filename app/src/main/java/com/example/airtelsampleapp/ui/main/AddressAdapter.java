package com.example.airtelsampleapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airtelsampleapp.R;
import com.example.airtelsampleapp.model.Address;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private static AddressAdapter sInstance;
    private Context mContext;
    private ArrayList<Address> addresses;

    private AddressAdapter(Context context){
        mContext = context;
    };

    public static AddressAdapter getsInstance(Context context){
        if(sInstance == null){
            sInstance = new AddressAdapter(context);
        }
        return sInstance;
    }

    public void setData(ArrayList<Address> addresses){
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_view_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(addresses.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        if(addresses != null) {
            return addresses.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
        }
    }
}
