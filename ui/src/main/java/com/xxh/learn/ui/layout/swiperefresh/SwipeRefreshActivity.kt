/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.xxh.learn.ui.layout.swiperefresh

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ViewAnimator
import androidx.fragment.app.FragmentTransaction
import com.xxh.learn.ui.R
import com.xxh.learn.ui.layout.common.activities.SampleActivityBase
import com.xxh.learn.ui.layout.common.logger.Log
import com.xxh.learn.ui.layout.common.logger.LogFragment
import com.xxh.learn.ui.layout.common.logger.LogWrapper
import com.xxh.learn.ui.layout.common.logger.MessageOnlyLogFilter

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * [android.support.v4.app.Fragment] which can display a view.
 *
 *
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
class SwipeRefreshActivity : SampleActivityBase() {
    // Whether the Log Fragment is currently shown
    private var mLogShown = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logger)

        if (savedInstanceState == null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//            val fragment = SwipeRefreshLayoutBasicFragment()
            val fragment = SwipeRefreshMultipleViewsFragment()
            transaction.replace(R.id.sample_content_fragment, fragment)
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val logToggle = menu.findItem(R.id.menu_toggle_log)
        logToggle.setVisible(findViewById<View>(R.id.sample_output) is ViewAnimator)
        logToggle.title =
            getString(if (mLogShown) R.string.sample_hide_log else R.string.sample_show_log)

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_toggle_log -> {
                mLogShown = !mLogShown
                val output: ViewAnimator = findViewById<View>(R.id.sample_output) as ViewAnimator
                if (mLogShown) {
                    output.setDisplayedChild(1)
                } else {
                    output.setDisplayedChild(0)
                }
                supportInvalidateOptionsMenu()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /** Create a chain of targets that will receive log data  */
    override fun initializeLogging() {
        // Wraps Android's native log framework.
        val logWrapper = LogWrapper()
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.logNode = logWrapper

        // Filter strips out everything except the message text.
        val msgFilter = MessageOnlyLogFilter()
        logWrapper.next = msgFilter

        // On screen logging via a fragment with a TextView.
        val logFragment: LogFragment = supportFragmentManager
            .findFragmentById(R.id.log_fragment) as LogFragment
        msgFilter.next = logFragment.logView

        Log.i(TAG, "Ready")
    }

    companion object {
        const val TAG: String = "MainActivity"
    }
}
