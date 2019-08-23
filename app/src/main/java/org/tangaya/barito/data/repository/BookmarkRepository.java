package org.tangaya.barito.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import org.tangaya.barito.data.model.BookmarkEntity;
import org.tangaya.barito.data.source.AppDatabase;

import java.util.List;

public class BookmarkRepository {

    private static BookmarkRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<BookmarkEntity>> mObservableBookmarks;

    private BookmarkRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableBookmarks = new MediatorLiveData<>();

        mObservableBookmarks.addSource(mDatabase.bookmarkDao().loadAllBookmarks(),
                bookmarkEntities -> {
                    if (mDatabase.isDatabaseCreated() != null) {
                        mObservableBookmarks.postValue(bookmarkEntities);
                    }
                });
    }

    public LiveData<BookmarkEntity> loadBookmark(final int bookmarkId) {
        return mDatabase.bookmarkDao().loadBookmark(bookmarkId);
    }
}
