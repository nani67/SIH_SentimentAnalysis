package com.example.stressbuster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyListAdapterForCounselling extends RecyclerView.Adapter<MyListAdapterForCounselling.ViewHolder> {

    List<MyListDataForCounselling> myListDataForCounsellings;


    MyListAdapterForCounselling(List<MyListDataForCounselling> myListDataForCounsellings) {
        this.myListDataForCounsellings = myListDataForCounsellings;

    }
    @Override
    public MyListAdapterForCounselling.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_layout_for_counselling, parent, false);
        return new MyListAdapterForCounselling.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final MyListAdapterForCounselling.ViewHolder holder, final int position) {

        holder.headingTextView.setText(myListDataForCounsellings.get(position).getTypeOfCounselling());
        holder.dateOfCounselling.setText(myListDataForCounsellings.get(position).getDateOfCounselling());
        holder.ratingBar.setRating(myListDataForCounsellings.get(position).getRatingValue());
        holder.durationOfCounselling.setText(myListDataForCounsellings.get(position).getDurationOfCounselling());
        holder.counsellorName.setText(myListDataForCounsellings.get(position).getCounsellorName());



    }

    @Override
    public int getItemCount() {
        return myListDataForCounsellings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView headingTextView;
        public TextView counsellorName;
        public TextView dateOfCounselling;
        public TextView durationOfCounselling;
        public RatingBar ratingBar;
        public ViewHolder(View itemView) {
            super(itemView);
            this.headingTextView = itemView.findViewById(R.id.typeOfCounselling);
            this.counsellorName = itemView.findViewById(R.id.personWhoCounselled);
            this.dateOfCounselling = itemView.findViewById(R.id.dateOfCounselling);
            this.durationOfCounselling = itemView.findViewById(R.id.timeOfCounselling);
            this.ratingBar = itemView.findViewById(R.id.ratingByStudent);
        }
    }
}
