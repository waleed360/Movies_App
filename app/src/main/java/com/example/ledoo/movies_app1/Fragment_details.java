package com.example.ledoo.movies_app1;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ledoo on 4/23/2016.
 */
public class Fragment_details extends Fragment {
    Db database;
    ImageView img;
    private String url;
    TextView txt;
    TextView release_date;
    TextView vote_count;
    TextView runtime;
    JsontaskTrailer jsontask;
    JsontaskReview jsontaskReview;
    JsontaskRuntime jsontaskRuntime;
    TextView overview;
    LinearListView l;
    LinearListView l2;
    Button save;
    Movies movie;

    ArrayList<String> list=new ArrayList<String>();

    String id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragmentdetails,container,false);
        img= (ImageView) v.findViewById(R.id.imageView2);
        database=new Db(getActivity());
txt=(TextView)v.findViewById(R.id.title);
        release_date=(TextView)v.findViewById(R.id.date);


        save=(Button)v.findViewById(R.id.save);

        l=(LinearListView)v.findViewById(R.id.trailer);


        //    save.setText(R.string.unFavorite);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String viewdata=database.searchdata(movie.getName());
               // Toast.makeText(getActivity(),viewdata+"ledoooo",Toast.LENGTH_LONG).show();
                if(movie.getId().equals(viewdata)){
                    Toast.makeText(getActivity(),"it is found in favorite",Toast.LENGTH_LONG).show();
                }else{
               // Toast.makeText(getActivity(),viewdata+"ledoooo",Toast.LENGTH_LONG).show();
                long idd=database.datainsert(movie.getName(),movie.getTitle(),movie.getRelease_date(),movie.getVote_count(),movie.getOverview(),movie.getId());
//                if(idd<0){
//                 //   Toast.makeText(getActivity(),"NOT Insert",Toast.LENGTH_LONG).show();
//                }
//                else{
//                   // Toast.makeText(getActivity(),"Insert",Toast.LENGTH_LONG).show();
//
//                }
            }}
        });
        vote_count=(TextView)v.findViewById(R.id.votecount);
        runtime=(TextView)v.findViewById(R.id.runtime);
        release_date=(TextView)v.findViewById(R.id.date);
        overview=(TextView)v.findViewById(R.id.overview);
        l2=(LinearListView)v.findViewById(R.id.review);


        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    public void changeData(Movies s) {




        movie=s;
        id=movie.getId();

            txt.setText(movie.title);
            overview.setText(movie.overview);

        jsontaskRuntime = new JsontaskRuntime();
        try {
            movie.runtime=jsontaskRuntime.execute("https://api.themoviedb.org/3/movie/"+movie.getId()+"?api_key="+Api_Key.api_key+"").get();
          //  Toast.makeText(getActivity(),movie.getId(),Toast.LENGTH_LONG).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        runtime.setText(movie.runtime);
            vote_count.setText(String.valueOf(movie.vote_count) + "/10");
            release_date.setText(movie.getRelease_date());

            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185" + movie.getName()).into(img);


            l.setOnItemClickListener(new LinearListView.OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, long id) {
                    Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + list.get(position));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    startActivity(intent);
                    //  Toast.makeText(getActivity(),list.get(position),Toast.LENGTH_LONG).show();
                }
            });


            jsontask = new JsontaskTrailer();

            jsontask.execute("https://api.themoviedb.org/3/movie/"+id+"/videos?api_key="+Api_Key.api_key+"");
            jsontaskReview = new JsontaskReview();
            jsontaskReview.execute("https://api.themoviedb.org/3/movie/"+id+"/reviews?api_key="+Api_Key.api_key+"");
    }

    class JsontaskTrailer extends AsyncTask<String, String, ArrayList<Movies>> {

        HttpURLConnection connection;
        BufferedReader reader;
        StringBuilder data;
        ArrayList<String> list2=new ArrayList<String>();
        String s;


        @Override
        protected ArrayList doInBackground(String... params) {
//            if(list.size()!=0){
//            list.clear();}
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connection.connect();
                InputStream inputStream = null;
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                Log.v("reader", reader + "walid");
                //buffer = new StringBuffer();
                String line = "";
                //  String data="";
                data = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    data.append(line);

                    Log.v("bufer", data.toString() + "walid");
                }


                JSONObject jsonObject = new JSONObject(data.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
                    s = finalobject.getString("key");
                    list2.add(s);
                    //Log.v("data", s + "walid");
                 //   Log.v( "id",s);



                }


            } catch (Exception e) {
                Log.v("error", e.getMessage() + "");

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return list2;

            }}

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);

            list=list2;
            int x=list2.size();
            ArrayList<String> li=new ArrayList<String>();
            for (int i=0;i<x;i++){
                int num=i+1;
                li.add("trailer "+num);
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,li);
            l.setAdapter(adapter);
        }
    }
    class JsontaskReview extends AsyncTask<String, String, ArrayList<String>> {

        HttpURLConnection connection;
        BufferedReader reader;
        StringBuilder data;
        ArrayList<String> list2=new ArrayList<String>();
        ArrayList<String> list3=new ArrayList<String>();
        String s=null;
        String content;


        @Override
        protected ArrayList<String> doInBackground(String... params) {
//            if(list.size()!=0){
//            list.clear();}
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connection.connect();
                InputStream inputStream = null;
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                Log.v("reader", reader + "walid");
                //buffer = new StringBuffer();
                String line = "";
                //  String data="";
                data = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    data.append(line);

                    Log.v("bufer", data.toString() + "walid");
                }


                JSONObject jsonObject = new JSONObject(data.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
                    s = finalobject.getString("author");
                    content=finalobject.getString("content");
                    list2.add(s);

                    list3.add(content);


                    Log.v("author", s);



                }


            } catch (Exception e) {
                Log.v("error", e.getMessage() + "");

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return list3;

            }}

        @Override
        protected void onPostExecute(ArrayList<String>s) {
            super.onPostExecute(s);


           Adapterr ad=new Adapterr(getActivity(),list2,list3);
           l2.setAdapter(ad);

        }
    }

/////////////////////////////////////
class JsontaskRuntime extends AsyncTask<String, String, String > {

    HttpURLConnection connection;
    BufferedReader reader;
    StringBuilder data;
    int time;



    @Override
    protected String doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.connect();
            InputStream inputStream = null;
            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            Log.v("reader", reader + "walid");
            //buffer = new StringBuffer();
            String line = "";
            //  String data="";
            data = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                data.append(line);

                Log.v("buferrrrrrrr", data.toString() + "walid");
            }


            JSONObject jsonObject = new JSONObject(data.toString());





             time = jsonObject.getInt("runtime");
            Log.d("fdjkhkjdklj",time+"");



            } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


     catch (Exception e) {
            Log.v("error", e.getMessage() + "");

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return time+"min";

        }}



}

    /////////////////////////////////////////////////////////////////////////////////////////////
    class Adapterr extends ArrayAdapter<String>{
        Context context;
    ArrayList<String>titlearray;
        ArrayList<String>descript;


        public Adapterr(Context c, ArrayList<String> mem,ArrayList<String> des) {
            super(c,R.layout.fragmentdetails,mem);
            this.context=c;

            this.titlearray=mem;
            this.descript=des;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View row=convertView;
            if(row ==null){

                LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row=inflater.inflate(R.layout.reviewadapter,parent,false);
            }

            TextView t1=(TextView)row.findViewById(R.id.author);
           TextView t2=(TextView)row.findViewById(R.id.content);
            t1.setText(titlearray.get(position));
            t2.setText(descript.get(position));
            return row;

        }
    }


}
