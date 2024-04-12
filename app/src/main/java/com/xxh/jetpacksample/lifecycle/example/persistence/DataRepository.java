package com.xxh.jetpacksample.lifecycle.example.persistence;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;


import com.xxh.jetpacksample.lifecycle.example.persistence.db.PersistenceAppDatabase;
import com.xxh.jetpacksample.lifecycle.example.persistence.db.entity.CommentEntity;
import com.xxh.jetpacksample.lifecycle.example.persistence.db.entity.ProductEntity;

import java.util.List;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final PersistenceAppDatabase mDatabase;
    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    private DataRepository(final PersistenceAppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(productEntities);
                    }
                });
    }

    public static DataRepository getInstance(final PersistenceAppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(int productId) {
        return mDatabase.commentDao().loadComments(productId);
    }

    public LiveData<List<ProductEntity>> searchProducts(String query) {
        return mDatabase.productDao().searchAllProducts(query);
    }
}
