package com.example.stressbuster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FacultyAwardedAdapter extends RecyclerView.Adapter<FacultyAwardedAdapter.ViewHolder> {

    List<FacultyDataReminders> facultyDataReminders;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    FacultyAwardedAdapter(List<FacultyDataReminders> facultyDataRemindersList) {
        this.facultyDataReminders = facultyDataRemindersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.rowlayoutforfacultyreminders, parent, false);
        return new FacultyAwardedAdapter.ViewHolder(listItem);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.assignmentInfo.setText(facultyDataReminders.get(position).getAssignmentInfo());
        holder.materialButton.setText(facultyDataReminders.get(position).getAwardedPoints());
        holder.materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, String> appleStuff = new HashMap<>();
                appleStuff.put("IdOfTheCompletedAssignment", facultyDataReminders.get(position).getUserId());
                appleStuff.put("UserWhoCompletedThat", firebaseUser.getEmail());
                appleStuff.put("awardedPointsToThatUser", facultyDataReminders.get(position).getAwardedPoints());

                firebaseFirestore.collection("studentsWhoCompletedStuff").document()
                        .set(appleStuff)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Map<String, String> awardedPointsStuffInfo = new HashMap<>();
                                awardedPointsStuffInfo.put("UserPoints", facultyDataReminders.get(position).getAwardedPoints());

                                firebaseFirestore.collection("UsersInfo")
                                        .document(firebaseUser.getUid())
                                        .collection("userPoints")
                                        .add(awardedPointsStuffInfo)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                            }
                                        });
                            }
                        });


            }
        });

    }

    @Override
    public int getItemCount() {
        return facultyDataReminders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentInfo;
        public MaterialButton materialButton;

        public ViewHolder(View itemView) {
            super(itemView);
            assignmentInfo = itemView.findViewById(R.id.assignmentDescription);
            materialButton = itemView.findViewById(R.id.getAwardedPoints);

        }
    }
}
