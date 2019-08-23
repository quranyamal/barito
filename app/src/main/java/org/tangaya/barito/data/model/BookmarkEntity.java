package org.tangaya.barito.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class BookmarkEntity implements Bookmark {
    @PrimaryKey
    private int id;

    private String title;
    private String url;

    public BookmarkEntity(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
