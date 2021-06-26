package com.swufe.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommentCalcActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "commentCalc";
    TextView textView;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_calc);

        String comment = getIntent().getStringExtra("comment");
        Log.i(TAG, "onCreate: comment= " + comment);
        ((TextView) findViewById(R.id.comment2)).setText(comment);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_collect) {
//            Intent collect = new Intent(this, CollectActivity.class);
//            collect.putExtra("collect", str);
//            startActivity(collect);
            textView = findViewById(R.id.comment2);
            str = textView.getText().toString();
            CommentItem item1 = new CommentItem();
            item1.setComment(str);
            Log.i(TAG, "onClick: "+item1);
            //插入数据库
            CollectManager manager = new CollectManager(this);
            long rowId = manager.add(item1);
            if (rowId != -1){
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
            }
            //manager.add(item1);
            finish();

/*            //测试数据库
            CommentItem item1 = new CommentItem("好看好看");
            CollectManager manager = new CollectManager(this);
            manager.add(item1);
            manager.add(new CommentItem("推荐"));
            Log.i(TAG, "onClick: 写入数据库完毕");
            
            //查询所有数据
            List<CommentItem> testlist = manager.listAll();
            for (CommentItem i : testlist){
                Log.i(TAG, "onClick: 取出数据[id="+i.getId()+"]Comment="+i.getComment());
            }
 */
        }
    }
}