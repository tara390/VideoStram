package com.manddprojectconsulant.videostram.Model;

import android.net.Uri;

public class ModelforDashboard {

    public String title;
    public String duration;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String thumb;
    Uri videouri;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Uri getVideouri() {
        return videouri;
    }

    public void setVideouri(Uri videouri) {
        this.videouri = videouri;
    }
}
