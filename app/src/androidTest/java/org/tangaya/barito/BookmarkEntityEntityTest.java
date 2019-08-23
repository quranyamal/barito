package org.tangaya.barito;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tangaya.barito.data.model.BookmarkEntity;
import org.tangaya.barito.data.source.AppDatabase;
import org.tangaya.barito.data.source.BookmarkDao;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class BookmarkEntityEntityTest {
    private BookmarkDao bookmarkDao;
    private AppDatabase db;

    private BookmarkEntity bookmarkEntityA = new BookmarkEntity(0, "title-A", "urlA");
    private BookmarkEntity bookmarkEntityB = new BookmarkEntity(1, "title-B","urlB");
    private BookmarkEntity bookmarkEntityC = new BookmarkEntity(2, "title-C", "urlC");

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        bookmarkDao = db.bookmarkDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertBookmark() {
        assertThat(bookmarkDao.getBookmarkCount(), equalTo(0));
        bookmarkDao.insertBookmark(bookmarkEntityA);
        bookmarkDao.insertBookmark(bookmarkEntityB);
        bookmarkDao.insertBookmark(bookmarkEntityC);

        assertThat(bookmarkDao.getBookmarkCount(), equalTo(3));
    }
}