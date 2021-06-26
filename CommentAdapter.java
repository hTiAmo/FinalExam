package com.swufe.finalexam;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends ArrayAdapter {
    public CommentAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Button btn_collect;
        private boolean flagCollect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_collect = itemView.findViewById(R.id.btn_collect);
            btn_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}
