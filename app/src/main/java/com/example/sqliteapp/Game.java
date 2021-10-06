package com.example.sqliteapp;

public class Game {
    private String id;
    private String name;
    private Integer status;

    public Game(String id, String gN, Integer gS){
        this.id = id;
        this.name = gN;
        this.status = gS;
    }

    public String getGameId(){
        return id;
    }

    public String getGameName(){
        return name;
    }

    public Integer getGameStatus(){
        return status;
    }

    public void setGameStatus(Integer status){
        this.status = status;
    }
}
