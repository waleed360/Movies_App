package com.example.ledoo.movies_app1;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.GridView;

import java.io.Serializable;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements Transfer{

    ArrayList<Movies> list = new ArrayList<Movies>();
FragmentManager f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f=getFragmentManager();
  //      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.framelayout, new FragmentShow())
//                    .commit();
        //}

        //setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.fragment, menu);
        return true;
    }


    @Override
    public void trans(Movies s) {
        Fragment_details fragment_details= (Fragment_details) f.findFragmentById(R.id.detailsFragment);
        if(fragment_details!=null){
            fragment_details.changeData(s);

        }else{
                    Bundle b=new Bundle();
            b.putSerializable("movie", (Serializable) s);
                            Intent intent = new Intent(this, DetailsActivity.class) ;
            intent.putExtra("mov",b);
            startActivity(intent);

        }
    }
}

