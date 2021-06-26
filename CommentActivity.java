package com.swufe.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    Handler handler;
    private String no = null;
    private String TAG = "comment";
    private List<HashMap<String,String>> Comment;//存放文字、图片信息
    private SimpleAdapter CommentAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_comment);
        initCommentView();

        this.setListAdapter(CommentAdapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    Comment = (List<HashMap<String, String>>) msg.obj;
                    CommentAdapter = new SimpleAdapter(CommentActivity.this, Comment,//listMovies的数据源
                            R.layout.activity_comment,//newMovies的XML布局
                            new String[]{"Comment"},
                            new int[]{R.id.comment}
                    );
                    setListAdapter(CommentAdapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
    }

    private void initCommentView() {
        Comment = new ArrayList<HashMap<String, String>>();
        for (int i=0;i<100;i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("Comment","comment: " + i);//影评
            Comment.add(map);
        }
        //生成适配器的Movie和动态数组对应的元素
        CommentAdapter = new SimpleAdapter(this,Comment,//listMovies数据源
                R.layout.activity_comment,//newMovies的XML布局
                new String[]{"Comment"},
                new int[]{R.id.comment}
        );
    }

    @Override
    public void run() {
        //获取网络数据，放入list带回到主线程中
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://movie.douban.com/review/best/?start=0").get();
            Log.i(TAG, "run: " + doc.title());
            Elements divs1 = doc.getElementsByTag("div");

//            for (Element div : divs){
//                Log.i(TAG, "run: div["+i+"]=" + div);
//                i++;
//            }
            Element div118 = divs1.get(17);
            Elements divs00 = div118.getElementsByTag("div");
            for (int i=6;i<divs00.size();i+=4){
                Element a = divs00.get(i);
                //Log.i(TAG, "run: text=" + a.text());

                String str1 = a.text();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("Comment",str1);//电影评价
                retList.add(map);
            }
//            for (Element div : divs){
//                Log.i(TAG, "run: div=" + div);
//                Log.i(TAG, "run: text=" + div.text());
//            }
            /*doc = Jsoup.connect("https://movie.douban.com/review/best/?start=20").get();
            Log.i(TAG, "run: " + doc.title());
            Elements divs2 = doc.getElementsByTag("div");
            Element div218 = divs1.get(17);
            Elements divs11 = div218.getElementsByTag("div");
            for (int i=6;i<divs00.size();i+=4){
                Element a = divs00.get(i);
                Log.i(TAG, "run: text=" + a.text());

                String str1 = a.text();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("Comment",str1);//电影评价
                retList.add(map);
            }*/


        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: parent= " + parent);
        Log.i(TAG, "onItemClick: view= " + view);
        Log.i(TAG, "onItemClick: position= " + position);
        Log.i(TAG, "onItemClick: id= " + id);
        HashMap<String,String> map = (HashMap<String,String>) getListView().getItemAtPosition(position);
        String commentStr = map.get("Comment");
        Log.i(TAG, "onItemClick: commentStr=" + commentStr);

        //打开新的页面，传入参数
        Intent commentCalc = new Intent(this,CommentCalcActivity.class);
        commentCalc.putExtra("comment",commentStr);
        startActivityForResult(commentCalc,1);
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 && resultCode==2){
            Bundle bundle = data.getExtras();
            no = bundle.getString("comment_collect","空");
            Log.i(TAG, "onActivityResult: " + no);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

     */

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //删除操作
        //Comment.remove(position);
        //CommentAdapter.notifyDataSetChanged();
        //构造对话框进行确认操作
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton
                ("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Comment.remove(position);
                        CommentAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}