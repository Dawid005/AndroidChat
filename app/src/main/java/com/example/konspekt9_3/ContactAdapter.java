package com.example.konspekt9_3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public ArrayList<ExampleComment> exampleComment;
    private OnItemClickListener mListener;
    public int FlagToEditComment = -1;
    private int position;
    public Button buttonEditComment;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface  OnItemClickListener{

        void onItemClick(int position);
    }

    public ContactAdapter(ArrayList<ExampleComment> exampleComments){
        exampleComment = exampleComments;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,
                parent,false);

        ContactViewHolder cvh = new ContactViewHolder(v, mListener);
        return cvh;
    }


    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ExampleComment currentItem = exampleComment.get(position);

        holder.content.setText(currentItem.getContent());
        holder.login.setText(currentItem.getLogin());
        holder.date.setText(currentItem.getDate());
        holder.id.setText(currentItem.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditComment = new Intent(holder.itemView.getContext(), EditComment.class);
                intentEditComment.putExtra("position",position);
                holder.itemView.getContext().startActivity(intentEditComment);
            }
        });


    }

    @Override
    public int getItemCount() {
        return exampleComment.size();
    }



    //@Override
    //public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
      //  removeItem(direction);
    //}

    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        public TextView content;
        public TextView login;
        public TextView date;
        public TextView id;
        public Button buttonEditComment;

            public ContactViewHolder(@NonNull View itemView, OnItemClickListener listener) {
                super(itemView);

                content = itemView.findViewById(R.id.textContent);
                login = itemView.findViewById(R.id.textLogin);
                date = itemView.findViewById(R.id.textDate);
                id = itemView.findViewById(R.id.textId);
/*
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = getAdapterPosition();
                            if (listener != null) {

                                if (position != RecyclerView.NO_POSITION) {
                                    listener.onItemClick(position);
                                }
                            }
                        }
                    });*/
            }
    }
}