package com.xxh.jetpacksample.lifecycle.source.livedata;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.xxh.jetpacksample.lifecycle.source.Lifecycle;
import com.xxh.jetpacksample.lifecycle.source.LifecycleEventObserver;
import com.xxh.jetpacksample.lifecycle.source.LifecycleOwner;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 数据倒灌：只收到对LiveData开始监听之后的值，开始监听之前的旧value不要回调给Observer
 * 解决数据倒灌的问题
 * 缺陷
 * 是对Event事件包装器一致性问题的改进，但未解决多观察者消费的问题，而且额外引入了消息未能从内存中释放的问题
 * @param <T>
 */
public class SingleLiveData<T> extends MutableLiveData<T> {

    private final HashMap<Observer<? super T>, AtomicBoolean> mPendingMap = new HashMap<>();

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
        Lifecycle lifecycle = owner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        mPendingMap.put(observer, new AtomicBoolean(false));
        lifecycle.addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_DESTROY) {
                mPendingMap.remove(observer);
            }
        });
        super.observe(owner, t -> {
            AtomicBoolean pending = mPendingMap.get(observer);
            //compareAndSet当前值对比expectedValue，相同则为true,并赋值newValue。
            if (pending != null && pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    @MainThread
    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        mPendingMap.put(observer, new AtomicBoolean(false));
        super.observeForever(observer);
    }

    @MainThread
    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        mPendingMap.remove(observer);
        super.removeObserver(observer);
    }

    @MainThread
    @Override
    public void removeObservers(@NonNull LifecycleOwner owner) {
        mPendingMap.clear();
        super.removeObservers(owner);
    }

    @MainThread
    @Override
    public void setValue(@Nullable T t) {
        for (AtomicBoolean value : mPendingMap.values()) {
            value.set(true);
        }
        super.setValue(t);
    }
}

