package com.swufe.finalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CollectManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public CollectManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public long add(CommentItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("comment",item.getComment());
        return db.insert(TBNAME,null, values);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("insert into comment(comment)"+"values(?)",new String[]{item.getComment()});
        //db.close();
    }

    public List<CommentItem> listAll(){
        List<CommentItem> commentList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            commentList = new ArrayList<CommentItem>();
            while(cursor.moveToNext()){
                CommentItem item = new CommentItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setComment(cursor.getString(cursor.getColumnIndex("COMMENT")));

                commentList.add(item);
            }
            cursor.close();
        }
        db.close();
        return commentList;

    }
}
