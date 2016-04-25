package com.example.ledoo.movies_app1;

import java.io.Serializable;

/**
 * Created by ledoo on 4/23/2016.
 */
public class Movies implements Serializable {

        String name;
        String title;
       String release_date;
        String vote_count;
    String runtime;
    String overview;
    String id;

        Movies(String n, String t,String d,String c,String o,String r,String id){

            this.name=n;
            this.title=t;
            this.release_date=d;
            this.vote_count=c;
            this.overview=o;
            this.runtime=r;
            this.id=id;
        }
        String getName(){return name;}
        String getTitle(){return title;}
        String getVote_count(){
            return vote_count;
       }
        String getRelease_date(){return release_date;}
        String getRuntime(){return runtime;}
        String getOverview(){return overview;}
         String getId(){return id;}

    }

