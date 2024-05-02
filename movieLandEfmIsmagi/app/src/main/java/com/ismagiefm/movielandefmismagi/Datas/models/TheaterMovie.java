package com.ismagiefm.movielandefmismagi.Datas.models;

import java.util.ArrayList;
import java.util.List;

public class TheaterMovie{
    public int id;
    public String title;
    public String synopsis;
    public String actors;
    public String directors;
    public String category;
    public String length;
    public String released_on;
    public Posters posters;
    private List<String> trailers;

    public List<String> getTrailers() {
        return trailers;
    }
    public Object getPosters() {
        return posters;
    }
}