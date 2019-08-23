package org.tangaya.barito.data.source;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.tangaya.barito.data.model.BookmarkEntity;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertBookmark(BookmarkEntity bookmarkEntity);

    @Delete
    public void deleteBookmark(BookmarkEntity bookmarkEntity);

    @Query("SELECT * FROM bookmarks WHERE id = :bookmarkId")
    LiveData<BookmarkEntity> loadBookmark(int bookmarkId);

    @Query("SELECT * FROM bookmarks")
    LiveData<List<BookmarkEntity>> loadAllBookmarks();

    @Query("SELECT count(*) FROM bookmarks")
    public int getBookmarkCount();
}
