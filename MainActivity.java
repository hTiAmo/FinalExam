package com.swufe.finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Runnable {
    private final String TAG = "main";
    private String no = null;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    Bundle bd1 = (Bundle) msg.obj;

                    Toast.makeText(MainActivity.this,"信息已更新",Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }

    public void openone(View btn) {
        //打开一个页面activity
        Log.i("open", "openone: ");
        if (btn.getId() == R.id.btn_all) {
            Intent hello = new Intent(this, AllActivity.class);
            startActivityForResult(hello, 1);
        }
        if (btn.getId() == R.id.btn_new) {
            Intent one = new Intent(this, CommentActivity.class);
            startActivityForResult(one, 4);
        }
        if (btn.getId() == R.id.btn_collect) {
            Intent one = new Intent(this, CollectActivity.class);
            startActivityForResult(one, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_set){
            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://movie.douban.com/"));
            startActivity(web);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {
        Log.i(TAG, "run: run()......");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //用于保存获取的信息
        Bundle bundle = new Bundle();
        //获取网络数据
        Document doc = null;
        try {
            doc = Jsoup.connect("https://movie.douban.com/chart").get();
            Log.i(TAG, "run: " + doc.title());
            Elements bodys = doc.getElementsByTag("table");
            //Document document = new Document("https://movie.douban.com/chart");
            Log.i(TAG, "run: "+bodys.size());
            for(int i =0;i<bodys.size();i++){
                Element body = bodys.get(i);
                //Log.i(TAG, "run: "+body.getElementsByTag("span").get(0).text());
                //Log.i(TAG, "run: "+body.getElementsByTag("p").get(0).text());
                Element div = bodys.get(i);
                //Log.i(TAG, "run: " + div.getElementsByTag("span").get(2).text());
                //Log.i(TAG, "run: " + div.getElementsByTag("span").get(3).text());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //获取Msg对象，用于返回主线程
        Message msg = handler.obtainMessage(5);
        //msg.obj = 5;
        msg.obj = bundle;
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}