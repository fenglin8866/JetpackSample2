package com.xxh.learn.ui.layout.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.xxh.learn.ui.layout.common.logger.Log
import com.xxh.learn.ui.layout.common.logger.LogWrapper

open class SampleActivityBase : AppCompatActivity() {
    val TAG: String = "BaseLoggerActivity"
    override fun onStart() {
        super.onStart()
        initializeLogging()
    }

    /** Set up targets to receive log data  */
    open fun initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        val logWrapper = LogWrapper()
        Log.logNode = logWrapper

        Log.i(TAG, "Ready")
    }
}