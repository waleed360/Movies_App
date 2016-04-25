package com.example.ledoo.movies_app1;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by ledoo on 4/23/2016.
 */
public class ViewHolder {


        ImageView myimage;
        ViewHolder(View v){

            this.myimage =(ImageView)v.findViewById(R.id.imageView);
        }
    }

