package com.example.stressbuster;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private Map<String, String> listdata;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public MyListAdapter(Map<String, String> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Object[] values = listdata.values().toArray();
        final Object[] firebaseReferences = listdata.keySet().toArray();
        Log.d("Length", values.length+"");
            holder.textView.setText(values[position].toString());
            Log.d("Adapter fu", values[position].toString());
            holder.deleteStuff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    firebaseFirestore.collection("UsersInfo").document(firebaseUser.getUid()).collection("reminders")
                            .document(firebaseReferences[position].toString())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                }
            });


    }




    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MaterialButton deleteStuff;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.name);
            this.deleteStuff = itemView.findViewById(R.id.delStuffRem);
        }
    }
}