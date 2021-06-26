package com.swufe.finalexam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    private CollectManager collectManager;
    private TextView li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        li = findViewById(R.id.list_item);
        collectManager = new CollectManager(this);

    }
    public void query(View view){
        //查询所有数据
        List<CommentItem> retlist = collectManager.listAll();
        showData(retlist);
        return;
    }
    //获取数据库中的数据
    private void showData(List<CommentItem> retlist){
        String comment = "";
        for (CommentItem commentItem : retlist){
            comment += "收藏自：" + commentItem.getComment() + "\n" + "\n";
        }
        li.setText(comment);
    }

}
