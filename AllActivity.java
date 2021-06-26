package com.swufe.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AllActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener {

    Handler handler;
    private String TAG = "all";
    private ArrayList<HashMap<String,String>> allMovies;//存放文字、图片信息
    private SimpleAdapter allMovieAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_all);
        initMovieView();

        //MyAdapter myAdapter = new MyAdapter(this,R.layout.activity_all,allMovies);
        //this.setListAdapter(myAdapter);
        this.setListAdapter(allMovieAdapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 9) {
                    List<HashMap<String, String>> all2 = (List<HashMap<String, String>>) msg.obj;
                    allMovieAdapter = new SimpleAdapter(AllActivity.this, all2,//listMovies的数据源
                            R.layout.activity_all,//allMovies的XML布局
                            new String[]{"Name", "Actor", "Grade", "Evaluate"},
                            new int[]{R.id.name, R.id.actor, R.id.grade, R.id.evaluate}
                    );
                    setListAdapter(allMovieAdapter);
                }
                super.handleMessage(msg);
            }
        };
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    private void initMovieView(){
        allMovies = new ArrayList<HashMap<String,String>>();
        for (int i=0;i<15;i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("Name","name:" + i);//电影名称
            map.put("Actor","actor:" + i);//详情描述
            map.put("Grade","grade: " + i);//电影评分
            map.put("Evaluate","evaluate：" + i);//电影评价
            allMovies.add(map);
        }
        //生成适配器的Movie和动态数组对应的元素
        allMovieAdapter = new SimpleAdapter(this,allMovies,//listMovies数据源
                R.layout.activity_all,//allMovies的XML布局
                new String[]{"Name","Actor","Grade","Evaluate"},
                new int[]{R.id.name,R.id.actor,R.id.grade,R.id.evaluate}
        );
    }

    @Override
    public void run() {
        //获取网络数据，放入all带回到主线程中
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://movie.douban.com/top250").get();
            Log.i(TAG, "run: " + doc.title());
            Elements ols = doc.getElementsByTag("ol");
            /*for (Element ol : ols){
                Log.i(TAG, "run: ol["+i+"]=" + ol);
                i++;
            }*/
            Element ol1 = ols.get(0);
            Elements lis = ol1.getElementsByTag("li");
            for (int i=0;i<lis.size();i+=2){
                Element aaa = lis.get(i);
                Log.i(TAG, "run: 1"+aaa.getElementsByTag("span").get(0).text());
                Log.i(TAG, "run: 2"+aaa.getElementsByTag("p").get(0).text());
                Log.i(TAG, "run: 3"+aaa.getElementsByTag("span").get(5).text());
                Log.i(TAG, "run: 4"+aaa.getElementsByTag("span").get(8).text());

                String str1 = aaa.getElementsByTag("span").get(0).text();
                String str2 = aaa.getElementsByTag("p").get(0).text();
                String str3 = aaa.getElementsByTag("span").get(5).text();
                String str4 = aaa.getElementsByTag("span").get(8).text();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("Name",str1);//电影名称
                map.put("Actor",str2);//详情描述
                map.put("Grade",str3);//电影评分
                map.put("Evaluate",str4);//电影评价
                retList.add(map);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(9);
        msg.obj = retList;
        handler.sendMessage(msg);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: parent=" + parent);
        Log.i(TAG, "onItemClick: views=" + view);
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: id=" + id);
    }
}