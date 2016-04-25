package com.example.ledoo.movies_app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ledoo on 4/25/2016.
 */
public class Db {
    Connection con;

    public Db(Context context) {
        con=new Connection(context);
    }
    public long datainsert( String url,String title,String release_date,String vote_count,String overview,String id){
        SQLiteDatabase sqLiteDatabase=con.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Connection.title,title);
        contentValues.put(Connection.name,url);
        contentValues.put(Connection.release_date,release_date);
        contentValues.put(Connection.vote_count,vote_count);
        contentValues.put(Connection.overview,overview);
        contentValues.put(Connection.id, id);
        long idd=sqLiteDatabase.insert(Connection.table_name, null, contentValues);

        return idd;

    }
    public  ArrayList<Movies> viewdata(){
        SQLiteDatabase sqLiteDatabase=con.getWritableDatabase();
        ArrayList<Movies>list2=new ArrayList<Movies>();
        String [] colums={Connection.name,Connection.title,Connection.release_date,Connection.vote_count,Connection.overview,Connection.id};
        Cursor cursor=sqLiteDatabase.query(Connection.table_name,colums,null,null,null,null,null,null);
        StringBuffer s=new StringBuffer();
        while (cursor.moveToNext()){

         //   Movies(String n, String t,String d,String c,String o,String r,String id)
            String name=cursor.getString(0);
            String title=cursor.getString(1);
            String overview=cursor.getString(4);
            String vote_count=cursor.getString(3);
            String id=cursor.getString(5);

            String release_date=cursor.getString(2);
            Movies c = new Movies(name, title,release_date,vote_count,overview,"",id);
            Log.v("data", s + "walid");
            list2.add(c);
          //  s.append(" "+name+" "+title+" "+overview +" " +vote_count+" "+id+" "+release_date+"\n");
        }
        return list2;
    }
    public  String searchdata(String n){
        String pass ="";
        SQLiteDatabase sqLiteDatabase=con.getWritableDatabase();
        String [] colums={Connection.id};
        Cursor cursor=sqLiteDatabase.query(Connection.table_name,colums,Connection.name+"= '"+n+"'",null,null,null,null,null);
        StringBuffer s=new StringBuffer();
        while (cursor.moveToNext()){

            String id=cursor.getString(0);
            pass+=id;
            // pass=cursor.getString(1);
        }
        return pass;
    }

    static class Connection extends SQLiteOpenHelper {
        private static final  String database_name="moviess";
        private static final  String table_name="movie";
        private static final  int database_version=4;
        private static final  String uid="uid";

        private static final String title="title";
    private static final String name="name";
        private static final  String release_date="data";
        private static final  String vote_count="count";
        private static final  String overview="overview";
        private static final  String id="id";


        private static final  String drop_table="DROP TABLE IF EXISTS "+table_name;
 private static final  String createtable= "CREATE TABLE "+table_name+
            " ("+uid+" INTEGER PRIMARY KEY AUTOINCREMENT, "+name+" VARCHAR(255), "+title+" VARCHAR(255), "+vote_count+" VARCHAR(255), "+overview+" VARCHAR(255), "+release_date+" VARCHAR(255), "+id+" VARCHAR(255))";
        private  Context context;


        public Connection(Context context) {
            super(context,database_name,null,database_version);
            this.context=context;
           // Toast.makeText(context, "con", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(createtable);
           // Toast.makeText(context,"create",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(drop_table);
           // Toast.makeText(context,"upgrade",Toast.LENGTH_LONG).show();

            onCreate(db);
        }}
}


