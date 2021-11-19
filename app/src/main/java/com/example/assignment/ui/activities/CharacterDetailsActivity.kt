package com.example.assignment.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.assignment.R
import com.example.assignment.databinding.ActivityCharacterDetailsBinding
import com.example.assignment.models.ApiResponse
import com.example.assignment.models.Location
import com.example.assignment.models.Results
import com.example.assignment.utils.KEY_CHARACTER
import com.example.assignment.utils.NOT_AVAILABLE
import com.example.assignment.utils.getImageHeight
import com.example.assignment.utils.isConnectedToInternet
import com.example.assignment.viewmodel.CharacterDetailsViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * This class is used to show character and location details
 */
class CharacterDetailsActivity : AppCompatActivity() {

    /**
     * Reference of view binding
     */
    private lateinit var binding: ActivityCharacterDetailsBinding

    /**
     * Reference of characterDetailsViewModel
     */
    private lateinit var mCharacterDetailsViewModel: CharacterDetailsViewModel

    /**
     * Reference for intent data
     */
    private var mCharacterDetails: Results? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCharacterDetails = intent.getParcelableExtra(KEY_CHARACTER)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        mCharacterDetailsViewModel = ViewModelProvider(this)[CharacterDetailsViewModel::class.java]
        checkNetworkStatus()
    }

    /**
     * This method is used to initialize view of activity
     */
    private fun initView() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        val layoutParam = binding.ivCharacter.layoutParams
        layoutParam.height = getImageHeight(applicationContext)
        binding.ivCharacter.layoutParams = layoutParam
        binding.txtResidents.visibility = View.GONE
        binding.txtDimension.visibility = View.GONE
    }

    /**
     * This method is used to check network status and make api call
     */
    private fun checkNetworkStatus() {
        mCharacterDetails?.let {
            loadCharacterDetails(it)
            it.location?.let { mLocation ->
                if (isConnectedToInternet(applicationContext)) {
                    getLocationDetails(mLocation)
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.no_internet_connection),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * This method is used to load character details in view
     * @param mCharacter character details
     */
    private fun loadCharacterDetails(mCharacter: Results) {
        Glide.with(this)
            .applyDefaultRequestOptions(RequestOptions.centerInsideTransform())
            .load(mCharacter.image).into(binding.ivCharacter)
        binding.toolbar.title =
            getString(R.string.character_title, mCharacter.name, mCharacter.status)
    }

    /**
     * This method is used to get location details from server and update view
     * @param mLocation location details for network request
     */
    private fun getLocationDetails(mLocation: Location) {
        binding.progressbarLocation.visibility = View.VISIBLE
        mCharacterDetailsViewModel.getLocationDetails(mLocation.url).observe(this, {
            binding.progressbarLocation.visibility = View.GONE
            when (it) {
                is ApiResponse.OnSuccess -> {
                    binding.txtResidents.visibility = View.VISIBLE
                    binding.txtDimension.visibility = View.VISIBLE
                    val size = it.result.residents?.size ?: 0
                    binding.txtDimension.text =
                        getString(R.string.dimensions, it.result.dimension ?: NOT_AVAILABLE)
                    binding.txtResidents.text = getString(R.string.residents, size)
                    binding.txtTypes.text =
                        getString(R.string.types, it.result.type ?: NOT_AVAILABLE)
                }
                is ApiResponse.OnError -> {
                    Snackbar.make(
                        binding.root,
                        it.errorMsg,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

}