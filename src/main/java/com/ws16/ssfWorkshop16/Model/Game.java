package com.ws16.ssfWorkshop16.Model;

public class Game {
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;
    private String url;
    private String image;

    
    public Game() {
    }


    public Game(Integer gid, String name, Integer year, Integer ranking, Integer users_rated, String url,
            String image) {
        this.gid = gid;
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.users_rated = users_rated;
        this.url = url;
        this.image = image;
    }


    public Integer getGid() {
        return gid;
    }


    public void setGid(Integer gid) {
        this.gid = gid;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Integer getYear() {
        return year;
    }


    public void setYear(Integer year) {
        this.year = year;
    }


    public Integer getRanking() {
        return ranking;
    }


    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }


    public Integer getUsers_rated() {
        return users_rated;
    }


    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return gid + ","+ name + "," + year + "," + ranking + ","
                + users_rated + "," + url + "," + image;
    }

    
}
