package com.example.assignment.listener

/**
 * Interface to handle pagination for recyclerview
 */
interface OnLoadMoreListener {

    /**
     * This method is called when recyclerview's onScroll method gets called
     */
    fun onLoadMore()
}