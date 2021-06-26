package com.swufe.finalexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {

    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, int resource, ArrayList<HashMap<String,String>> list){
        super(context,resource,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View allmoviesView = convertView;
        if (allmoviesView == null) {
            allmoviesView = LayoutInflater.from(getContext()).inflate(R.layout.activity_all, parent, false);
        }
        Map<String,String> map = (Map<String, String>) getItem(position);
        TextView name = (TextView) allmoviesView.findViewById(R.id.name);
        TextView actor = (TextView) allmoviesView.findViewById(R.id.actor);
        TextView grade = (TextView) allmoviesView.findViewById(R.id.grade);
        TextView evaluate = (TextView) allmoviesView.findViewById(R.id.evaluate);

        name.setText("Name:" + map.get("name"));
        actor.setText("Actor:" + map.get("actor"));
        grade.setText("Grade:" + map.get("grade"));
        evaluate.setText("Evaluate:" + map.get("evaluate"));

        return allmoviesView;
    }
}
