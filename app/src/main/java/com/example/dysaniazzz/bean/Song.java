package com.example.dysaniazzz.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by DysaniazzZ on 17/01/2017.
 * 第六章：LitePalDemo的实体类
 */
public class Song extends DataSupport {

    @Column(nullable = false)
    private String name;

    private int duration;

    private Album album;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
