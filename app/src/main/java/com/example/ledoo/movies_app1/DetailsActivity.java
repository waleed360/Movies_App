package com.example.ledoo.movies_app1;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;


/**
 * Created by ledoo on 4/24/2016.
 */
public class DetailsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.framdetails, new Fragment_details())
//                    .commit();
//        }
        Intent intent = this.getIntent();
        if (intent != null ) {
            Bundle b = intent.getBundleExtra("mov");
            Movies movie = (Movies) b.getSerializable("movie");
            FragmentManager f=getFragmentManager();
            Fragment_details fragment_details= (Fragment_details) f.findFragmentById(R.id.detailsFragment);
            fragment_details.changeData(movie);
        }
    }
}
