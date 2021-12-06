package com.example.assignment.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.R
import com.example.assignment.databinding.ActivityDashboardBinding
import com.example.assignment.listener.LoadMoreScroll
import com.example.assignment.listener.OnLoadMoreListener
import com.example.assignment.models.ApiResponse
import com.example.assignment.models.CharacterList
import com.example.assignment.models.Results
import com.example.assignment.ui.adapters.CharacterAdapter
import com.example.assignment.utils.BOTTOM_VERTICAL_BIAS
import com.example.assignment.utils.CENTER_VERTICAL_BIAS
import com.example.assignment.utils.isConnectedToInternet
import com.example.assignment.viewmodel.DashboardViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * This class is used to fetch character list from server.
 * and to load list of character in view
 */
class DashboardActivity : AppCompatActivity() {

    /**
     * Reference for view binding of layout
     */
    private lateinit var holder: ActivityDashboardBinding

    /**
     * Reference for dashboardViewModel
     */
    private lateinit var mDashboardViewModel: DashboardViewModel

    /**
     * To maintain character list data from server
     */
    private val mCharacterList = ArrayList<Results>()

    /**
     * To load character list data in recyclerview
     */
    private val mCharacterListAdapter = CharacterAdapter(mCharacterList)

    /**
     * To implement pagination for recylcerview's onScroll
     */
    private lateinit var mScrollListener: LoadMoreScroll

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        holder = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(holder.root)
        mDashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        initRecyclerView()
        setUpSearchView()
        checkNetworkStatus(mDashboardViewModel.getSearchString())
    }

    /**
     * This method is used to initialize recycler view properties
     */
    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        holder.recyclerView.apply {
            adapter = mCharacterListAdapter
            layoutManager = linearLayoutManager
        }
        mScrollListener = LoadMoreScroll(linearLayoutManager)
        mScrollListener.setLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                getCharacterList(
                    mDashboardViewModel.getNextPageUrl(),
                    mDashboardViewModel.getSearchString()
                )
            }
        })
        holder.recyclerView.addOnScrollListener(mScrollListener)
    }

    /**
     * This method is used to check internet connection and make rest api call
     */
    private fun checkNetworkStatus(searchString: String?) {
        if (isConnectedToInternet(applicationContext)) {
            getCharacterList(mDashboardViewModel.getNextPageUrl(), searchString)
        } else {
            Snackbar.make(
                holder.root,
                getString(R.string.no_internet_connection),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * This method is used to execute REST API call and update the view
     * @param pageUrl reference for pagination url
     */
    private fun getCharacterList(pageUrl: String?, searchString: String?) {
        pageUrl?.let {
            showProgressBar()
            mDashboardViewModel.getCharacterList(pageUrl, searchString).observe(this, {
                holder.progressbar.visibility = View.GONE
                when (it) {
                    is ApiResponse.OnSuccess -> {
                        mDashboardViewModel.setNextPageUrl(it.result.info?.next)
                        updateList(it.result)
                    }
                    is ApiResponse.OnError -> {
                        Snackbar.make(holder.root, it.errorMsg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    /**
     * This method is used to show progressbar on screen depending upon pagination
     */
    private fun showProgressBar() {
        holder.progressbar.visibility = View.VISIBLE
        holder.progressbar.updateLayoutParams<ConstraintLayout.LayoutParams> {
            verticalBias = if (mCharacterList.isEmpty()) {
                CENTER_VERTICAL_BIAS
            } else {
                BOTTOM_VERTICAL_BIAS
            }
        }
    }

    /**
     * This is method is used append data received from server and update it to recylcerview
     * @param characterList list received from server
     */
    private fun updateList(characterList: CharacterList) {
        characterList.results?.let { it ->
            val lastSize = mCharacterList.size
            mCharacterList.addAll(it)
            val updatedSize = mCharacterList.size
            mCharacterListAdapter.update(mCharacterList, lastSize, updatedSize)
            mScrollListener.setLoading(false)
        }
    }

    private fun setUpSearchView() {
        val editText = findViewById<EditText>(R.id.etSearch)
        editText.doAfterTextChanged {
            it?.let {
                mDashboardViewModel.setSearchString(it.toString())
                checkNetworkStatus(mDashboardViewModel.getSearchString())
            }
        }
    }

}