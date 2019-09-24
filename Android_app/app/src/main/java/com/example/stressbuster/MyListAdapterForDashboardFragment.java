package com.example.stressbuster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

public class MyListAdapterForDashboardFragment extends RecyclerView.Adapter<MyListAdapterForDashboardFragment.ViewHolder> {
    private Map<String, String> listdata;

    public MyListAdapterForDashboardFragment(Map<String, String> listdata) {
        this.listdata = listdata;
    }
    @Override
    public MyListAdapterForDashboardFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_layout_for_dashboard, parent, false);
        return new MyListAdapterForDashboardFragment.ViewHolder(listItem);
    }


    @Override
    public void onBindViewHolder(final MyListAdapterForDashboardFragment.ViewHolder holder, final int position) {
        Object[] values = listdata.values().toArray();
        holder.textView.setText(values[position].toString());
    }

    @Override
    public int getItemCount() {
        int limit = 3;
        if(listdata.size() > limit){
            return limit;
        }
        else
        {
            return listdata.size();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.name);
        }
    }
}
