package com.example.ledoo.movies_app1;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
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
 * Created by ledoo on 3/27/2016.
 */
public class FragmentShow extends Fragment {
    GridView mugrid;
    Db database;
    ArrayList<Movies> list = new ArrayList<Movies>();
    Jsontask jsontask;
    Transfer transfer;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        transfer = (Transfer) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);
        mugrid = (GridView) v.findViewById(R.id.gridView);
       // Toast.makeText(getActivity(), "fragment", Toast.LENGTH_LONG).show();
        database = new Db(getActivity());

        mugrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), position + "", Toast.LENGTH_LONG).show();
                transfer.trans(list.get(position));

            }
        });
        return v;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().getFragmentManager().findFragmentById(R.id.detailsFragment) != null) {

             hid();}
        jsontask = new Jsontask();

        jsontask.execute("https://api.themoviedb.org/3/movie/popular?api_key="+Api_Key.api_key+"");


    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activ000000ity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.most_popular) {
            // mugrid.removeAllViewsInLayout();
            //  list.clear();

            jsontask = new Jsontask();

            jsontask.execute("https://api.themoviedb.org/3/movie/popular?api_key=" + Api_Key.api_key + "");


          //  Toast.makeText(getActivity(), "popular", Toast.LENGTH_LONG).show();

            return true;
        }
        if (id == R.id.top_rated) {


            jsontask = new Jsontask();
            jsontask.execute("https://api.themoviedb.org/3/movie/top_rated?api_key=" + Api_Key.api_key + "");

//            Toast.makeText(getActivity(), "top rated", Toast.LENGTH_LONG).show();

            return true;
        }
        if (id == R.id.Favorite) {
            list = database.viewdata();
            if (getActivity().getFragmentManager().findFragmentById(R.id.detailsFragment) != null) {
                if (list != null && list.size() > 0) {
                    transfer.trans(list.get(0));
               //     Toast.makeText(getActivity(), database.viewdata().get(0).getTitle() + "", Toast.LENGTH_LONG).show();
                    show();
                } else {

                    hid();
                }
            }
            Vdapter adapter = new Vdapter(getActivity(), list);
            mugrid.setAdapter(adapter);

        }

        return super.onOptionsItemSelected(item);
    }

    public void hid() {
        getActivity().getFragmentManager().beginTransaction().hide(getActivity().getFragmentManager().findFragmentById(R.id.detailsFragment)).commit();
    }

    public void show() {
        getActivity().getFragmentManager().beginTransaction().show(getActivity().getFragmentManager().findFragmentById(R.id.detailsFragment)).commit();

    }

    //    public void useAdapter(ArrayList<Movies> l) {
//        Vdapter adapter = new Vdapter(getActivity(), l);
//        mugrid.setAdapter(adapter);
//    }
    class Jsontask extends AsyncTask<String, String, ArrayList<Movies>> {

        HttpURLConnection connection;
        BufferedReader reader;
        StringBuilder data;

        // StringBuffer buffer;
        String s = null;


        String title;
        String release_date;
        String vote_count;
        String runtime;
        String overview;
        String id;
        ArrayList<Movies> list2 = new ArrayList<Movies>();

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
                //runtime=jsonObject.getInt("runtime");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalobject = jsonArray.getJSONObject(i);
                    s = finalobject.getString("poster_path");
                    title = finalobject.getString("title");
                    vote_count = finalobject.getString("vote_average");
                    release_date = finalobject.getString("release_date");

                    //   runtime=finalobject.getString("runtime");
                    overview = finalobject.getString("overview");
                    id = finalobject.getString("id");
                    // Log.v( "release_date", id);

                    Movies c = new Movies(s, title, release_date, vote_count, overview, runtime, id);
                    Log.v("data", s + "walid");
                    list2.add(c);


                }

                Log.v("data", list2.size() + "walid");
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

            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            list = list2;
            if (getActivity().getFragmentManager().findFragmentById(R.id.detailsFragment) != null) {
                transfer.trans(list.get(0));
                show();
            }

            Vdapter adapter = new Vdapter(getActivity(), list);
            mugrid.setAdapter(adapter);


        }
    }

}
