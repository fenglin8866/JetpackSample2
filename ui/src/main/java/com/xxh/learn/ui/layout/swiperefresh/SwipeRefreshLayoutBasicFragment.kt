/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xxh.learn.ui.layout.swiperefresh

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.xxh.learn.ui.R
import com.xxh.learn.ui.layout.common.dummydata.Cheeses
import com.xxh.learn.ui.layout.common.logger.Log

/**
 * A basic sample that shows how to use [SwipeRefreshLayout] to add
 * the 'swipe-to-refresh' gesture to a layout. In this sample, SwipeRefreshLayout contains a
 * scrollable [ListView] as its only child.
 *
 *
 * To provide an accessible way to trigger the refresh, this app also provides a refresh
 * action item.
 *
 *
 * In this sample app, the refresh updates the ListView with a random set of new items.
 */
class SwipeRefreshLayoutBasicFragment : Fragment() {
    /**
     * The [android.support.v4.widget.SwipeRefreshLayout] that detects swipe gestures and
     * triggers callbacks in the app.
     */
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    /**
     * The [ListView] that displays the content that should be refreshed.
     */
    private lateinit var mListView: ListView

    /**
     * The [android.widget.ListAdapter] used to populate the [ListView]
     * defined in the previous statement.
     */
    private lateinit var mListAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Notify the system to allow an options menu for this fragment.
        setHasOptionsMenu(true)
    }

    // BEGIN_INCLUDE (inflate_view)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_swiper_refresh_basic, container, false)

        // Retrieve the SwipeRefreshLayout and ListView instances
        mSwipeRefreshLayout = view.findViewById<View>(R.id.swiperefresh) as SwipeRefreshLayout

        // BEGIN_INCLUDE (change_colors)
        // Set the color scheme of the SwipeRefreshLayout by providing 4 color resource ids
        mSwipeRefreshLayout.setColorScheme(
            R.color.swipe_color_1, R.color.swipe_color_2,
            R.color.swipe_color_3, R.color.swipe_color_4
        )

        // END_INCLUDE (change_colors)

        // Retrieve the ListView
        mListView = view.findViewById<View>(android.R.id.list) as ListView

        return view
    }

    // END_INCLUDE (inflate_view)
    // BEGIN_INCLUDE (setup_views)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Create an ArrayAdapter to contain the data for the ListView. Each item in the ListView
         * uses the system-defined simple_list_item_1 layout that contains one TextView.
         */
        mListAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            Cheeses.randomList(LIST_ITEM_COUNT)
        )

        // Set the adapter between the ListView and its backing data.
        mListView!!.adapter = mListAdapter

        // BEGIN_INCLUDE (setup_refreshlistener)
        /**
         * Implement [SwipeRefreshLayout.OnRefreshListener]. When users do the "swipe to
         * refresh" gesture, SwipeRefreshLayout invokes
         * [onRefresh()][SwipeRefreshLayout.OnRefreshListener.onRefresh]. In
         * [onRefresh()][SwipeRefreshLayout.OnRefreshListener.onRefresh], call a method that
         * refreshes the content. Call the same method in response to the Refresh action from the
         * action bar.
         */
        mSwipeRefreshLayout.setOnRefreshListener {
            Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout")

            initiateRefresh()
        }
        // END_INCLUDE (setup_refreshlistener)
    }

    // END_INCLUDE (setup_views)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    // BEGIN_INCLUDE (setup_refresh_menu_listener)
    /**
     * Respond to the user's selection of the Refresh action item. Start the SwipeRefreshLayout
     * progress bar, then initiate the background task that refreshes the content.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                Log.i(LOG_TAG, "Refresh menu item selected")

                // We make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true)
                }

                // Start our refresh background task
                initiateRefresh()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // END_INCLUDE (setup_refresh_menu_listener)
    // BEGIN_INCLUDE (initiate_refresh)
    /**
     * By abstracting the refresh process to a single method, the app allows both the
     * SwipeGestureLayout onRefresh() method and the Refresh action item to refresh the content.
     */
    private fun initiateRefresh() {
        Log.i(LOG_TAG, "initiateRefresh")

        /**
         * Execute the background task, which uses [AsyncTask] to load the data.
         */
        DummyBackgroundTask().execute()
    }

    // END_INCLUDE (initiate_refresh)
    // BEGIN_INCLUDE (refresh_complete)
    /**
     * When the AsyncTask finishes, it calls onRefreshComplete(), which updates the data in the
     * ListAdapter and turns off the progress bar.
     */
    private fun onRefreshComplete(result: List<String?>) {
        Log.i(LOG_TAG, "onRefreshComplete")

        // Remove all items from the ListAdapter, and then replace them with the new items
        mListAdapter.clear()
        for (cheese in result) {
            mListAdapter.add(cheese)
        }

        // Stop the refreshing indicator
        mSwipeRefreshLayout.setRefreshing(false)
    }

    // END_INCLUDE (refresh_complete)
    /**
     * Dummy [AsyncTask] which simulates a long running task to fetch new cheeses.
     */
    private inner class DummyBackgroundTask : AsyncTask<Void?, Void?, List<String?>?>() {
        val TASK_DURATION: Int = 3 * 1000 // 3 seconds
        override fun onPostExecute(result: List<String?>?) {
            super.onPostExecute(result)
            // Tell the Fragment that the refresh has completed
            onRefreshComplete(result!!)
        }

        override fun doInBackground(vararg params: Void?): List<String?>? {
            try {
                Thread.sleep(TASK_DURATION.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            // Return a new random list of cheeses
            return Cheeses.randomList(LIST_ITEM_COUNT)
        }

    }

    companion object {
        private val LOG_TAG: String = SwipeRefreshLayoutBasicFragment::class.java.simpleName

        private const val LIST_ITEM_COUNT = 20
    }
}
