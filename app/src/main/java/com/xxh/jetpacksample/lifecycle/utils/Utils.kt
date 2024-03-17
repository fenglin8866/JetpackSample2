package com.xxh.jetpacksample.lifecycle.utils

import android.util.Log
import com.xxh.jetpacksample.lifecycle.utils.FastSafeIterableMap

class Utils {

    fun fastSafeIterableMapTest(){

        val observerMap =
            FastSafeIterableMap<String, Int>()
        observerMap.putIfAbsent("A", 1)
        observerMap.putIfAbsent("B", 2)
        observerMap.putIfAbsent("C", 3)
        observerMap.putIfAbsent("D", 4)
        observerMap.putIfAbsent("E", 5)
        val descendingIterator = observerMap.descendingIterator()
        while (descendingIterator.hasNext()) {
            val (key, value) = descendingIterator.next()
            Log.i("xxh", "key=$key value=$value")
            /**
            2023-03-22 19:44:53.385 11100-11100/com.lifecycle.app I/xxh: key=E value=5
            2023-03-22 19:44:53.385 11100-11100/com.lifecycle.app I/xxh: key=D value=4
            2023-03-22 19:44:53.385 11100-11100/com.lifecycle.app I/xxh: key=C value=3
            2023-03-22 19:44:53.385 11100-11100/com.lifecycle.app I/xxh: key=B value=2
            2023-03-22 19:44:53.385 11100-11100/com.lifecycle.app I/xxh: key=A value=1
             */
        }
        Log.i("xxh", "---------------------------------------")
        val ascendingIterator = observerMap.iteratorWithAdditions()
        while (ascendingIterator.hasNext()) {
            val (key, value) = ascendingIterator.next()
            Log.i("xxh1", "key=$key value=$value")
            /*
                2023-03-22 20:04:36.772 16245-16245/com.lifecycle.app I/xxh1: key=A value=1
                2023-03-22 20:04:36.772 16245-16245/com.lifecycle.app I/xxh1: key=B value=2
                2023-03-22 20:04:36.772 16245-16245/com.lifecycle.app I/xxh1: key=C value=3
                2023-03-22 20:04:36.772 16245-16245/com.lifecycle.app I/xxh1: key=D value=4
                2023-03-22 20:04:36.772 16245-16245/com.lifecycle.app I/xxh1: key=E value=5
             */
        }
    }
}