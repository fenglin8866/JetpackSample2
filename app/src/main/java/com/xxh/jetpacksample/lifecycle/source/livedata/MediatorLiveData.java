package com.xxh.jetpacksample.lifecycle.source.livedata;


import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xxh.jetpacksample.lifecycle.utils.SafeIterableMap;

import java.util.Map;

/**
 * {@link  LiveData} subclass which may observe other {@code LiveData} objects and react on
 * {@code OnChanged} events from them.
 * <p>
 * This class correctly propagates its active/inactive states down to source {@code LiveData}
 * objects.
 * <p>
 * Consider the following scenario: we have 2 instances of {@code LiveData}, let's name them
 * {@code liveData1} and {@code liveData2}, and we want to merge their emissions in one
 * {@link  MediatorLiveData} object: {@code liveDataMerger}. Then, {@code liveData1} and {@code
 * liveData2} will become sources for the {@code liveDataMerger} and every time {@code onChanged}
 * callback is called for either of them, we set a new value in {@code liveDataMerger}.
 *
 * <pre>
 * LiveData&lt;Integer&gt; liveData1 = ...;
 * LiveData&lt;Integer&gt; liveData2 = ...;
 *
 * MediatorLiveData&lt;Integer&gt; liveDataMerger = new MediatorLiveData&lt;&gt;();
 * liveDataMerger.addSource(liveData1, value -&gt; liveDataMerger.setValue(value));
 * liveDataMerger.addSource(liveData2, value -&gt; liveDataMerger.setValue(value));
 * </pre>
 * <p>
 * Let's consider that we only want 10 values emitted by {@code liveData1}, to be
 * merged in the {@code liveDataMerger}. Then, after 10 values, we can stop listening to {@code
 * liveData1} and remove it as a source.
 * <pre>
 * liveDataMerger.addSource(liveData1, new Observer&lt;Integer&gt;() {
 *      private int count = 1;
 *
 *      {@literal @}Override public void onChanged(@Nullable Integer s) {
 *          count++;
 *          liveDataMerger.setValue(s);
 *          if (count &gt; 10) {
 *              liveDataMerger.removeSource(liveData1);
 *          }
 *      }
 * });
 * </pre>
 *
 * @param <T> The type of data hold by this instance/ˈmiːdieɪtər/
 */
@SuppressWarnings("WeakerAccess")
public class MediatorLiveData<T> extends MutableLiveData<T> {
    private SafeIterableMap<LiveData<?>, Source<?>> mSources = new SafeIterableMap<>();

    /**
     * Creates a MediatorLiveData with no value assigned to it.
     */
    public MediatorLiveData() {
        super();
    }

    /**
     * Creates a MediatorLiveData initialized with the given {@code value}.
     *
     * @param value initial value
     */
    public MediatorLiveData(T value) {
        super(value);
    }

    /**
     * todo：怎么实现与LiveData之间的关联
     * Starts to listen to the given {@code source} LiveData, {@code onChanged} observer will be
     * called when {@code source} value was changed.
     * <p>
     * {@code onChanged} callback will be called only when this {@code MediatorLiveData} is active.
     * <p> If the given LiveData is already added as a source but with a different Observer,
     * {@link IllegalArgumentException} will be thrown.
     *
     * @param source    the {@code LiveData} to listen to
     * @param onChanged The observer that will receive the events
     * @param <S>       The type of data hold by {@code source} LiveData
     */
    @MainThread
    public <S> void addSource(@NonNull LiveData<S> source, @NonNull Observer<? super S> onChanged) {
        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        MediatorLiveData.Source<S> e = new MediatorLiveData.Source<>(source, onChanged);
        MediatorLiveData.Source<?> existing = mSources.putIfAbsent(source, e);
        if (existing != null && existing.mObserver != onChanged) {
            throw new IllegalArgumentException(
                    "This source was already added with the different observer");
        }
        if (existing != null) {
            return;
        }
        if (hasActiveObservers()) {
            e.plug();
        }
    }

    /**
     * Stops to listen the given {@code LiveData}.
     *
     * @param toRemote {@code LiveData} to stop to listen
     * @param <S>      the type of data hold by {@code source} LiveData
     */
    @MainThread
    public <S> void removeSource(@NonNull LiveData<S> toRemote) {
        MediatorLiveData.Source<?> source = mSources.remove(toRemote);
        if (source != null) {
            source.unplug();
        }
    }

    @CallSuper
    @Override
    protected void onActive() {
        for (Map.Entry<LiveData<?>, MediatorLiveData.Source<?>> source : mSources) {
            source.getValue().plug();//plʌɡ
        }
    }

    @CallSuper
    @Override
    protected void onInactive() {
        for (Map.Entry<LiveData<?>, MediatorLiveData.Source<?>> source : mSources) {
            source.getValue().unplug();
        }
    }

    /**
     *  封了LiveData和对应的Observer
     *  Source中mVersion与LiveData中Version同步。
     *  当Version不一致时更新传入Observer
     * @param <V>
     */
    private static class Source<V> implements Observer<V> {
        final LiveData<V> mLiveData;
        final Observer<? super V> mObserver;
        int mVersion = START_VERSION;

        Source(LiveData<V> liveData, final Observer<? super V> observer) {
            mLiveData = liveData;
            mObserver = observer;
        }

        void plug() {
            mLiveData.observeForever(this);
        }

        void unplug() {
            mLiveData.removeObserver(this);
        }

        @Override
        public void onChanged(@Nullable V v) {
            if (mVersion != mLiveData.getVersion()) {
                mVersion = mLiveData.getVersion();
                mObserver.onChanged(v);
            }
        }
    }
}
