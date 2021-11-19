package com.example.assignment.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.utils.DEFAULT_ZERO
import com.example.assignment.utils.VISIBLE_THRESHOLD

/**
 * This class is used to implement endless scroll listener for recyclerview
 */
class LoadMoreScroll(layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    /**
     * Layout manager of recyclerview
     */
    private var mLayoutManager: RecyclerView.LayoutManager = layoutManager

    /**
     * Threshold value for implementing endless scroll listener
     */
    private var mVisibleThreshHold = VISIBLE_THRESHOLD

    /**
     * To handle REST API call with recyclerview scroll effect
     */
    private var isLoading = false

    /**
     * Interface variable to handle pagination
     */
    private lateinit var mLoadMoreListener: OnLoadMoreListener

    /**
     * Last visible item of current list
     */
    private var mLastVisibleItem = DEFAULT_ZERO

    /**
     * Total item count of current list
     */
    private var mTotalItemCount = DEFAULT_ZERO


    /**
     * This method is used to get current loading status
     */
    fun isLoading(): Boolean = isLoading

    /**
     * This method is used to set current loading status
     */
    fun setLoading(loadMoreStatus: Boolean) {
        isLoading = loadMoreStatus
    }

    /**
     * This method is used to set listener for implementing endless scroll
     * @param listener to handle onScroll listener
     */
    fun setLoadMoreListener(listener: OnLoadMoreListener) {
        mLoadMoreListener = listener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= DEFAULT_ZERO) return
        mTotalItemCount = mLayoutManager.itemCount
        if (mLayoutManager is LinearLayoutManager) {
            mLastVisibleItem = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }
        if (!isLoading() && mTotalItemCount <= mLastVisibleItem + mVisibleThreshHold) {
            mLoadMoreListener.onLoadMore()
            setLoading(true)
        }
    }

}