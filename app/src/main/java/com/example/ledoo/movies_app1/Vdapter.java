package com.example.ledoo.movies_app1;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ledoo on 4/23/2016.
 */
public class Vdapter extends BaseAdapter {

        Context contex;
        //ArrayList<Movies> list2;
        ArrayList<Movies> list2=new ArrayList<Movies>();
        Vdapter(Context c,ArrayList<Movies> l){
            contex=c;
            list2=l;
            Resources res =c.getResources();


        }
        @Override
        public int getCount() {
            return list2.size();
        }

        @Override
        public Object getItem(int position) {
            return list2.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)  {
            // Toast.makeText(MainActivity.this,"a7aaaaadabtergetview",Toast.LENGTH_LONG).show();
            View row=convertView;
            ViewHolder holder=null;
            if(row==null) {
                LayoutInflater l = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = l.inflate(R.layout.second, parent, false);
                holder=new ViewHolder(row);
                row.setTag(holder);
            }else{
                holder=(ViewHolder)row.getTag();
            }
            //ImageView v=(ImageView)row.findViewById(R.id.imageView);


            //   holder.myimage.setImageResource(temp.name);

            Picasso.with(contex).load("http://image.tmdb.org/t/p/w185"+  list2.get(position).name).into(holder.myimage);
            //  holder.myimage.setTag(temp);
            return row;
        }
    }
