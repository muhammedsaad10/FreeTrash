package com.example.freetrash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context mcontext;
    ArrayList<User> dataList;
//    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");


    public RecyclerAdapter(Context mcontext, ArrayList<User> dataList) {
        this.mcontext = mcontext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.user_list_layout,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText("User-" + (position+1) );
        holder.username.setText("Name : "+dataList.get(position).getUsername());
        holder.password.setText("Password : "+dataList.get(position).getPassword());

//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new UpdateUser1().deleteUser(position);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,username,password;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.dataTitle);
            username = itemView.findViewById(R.id.dataName);
            password = itemView.findViewById(R.id.dataPassword);
//            layout = itemView.findViewById(R.id.list_layout);
        }
    }
}
