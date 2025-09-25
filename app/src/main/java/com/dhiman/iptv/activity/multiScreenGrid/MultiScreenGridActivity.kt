package com.dhiman.iptv.activity.multiScreenGrid

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.ActivityMultiScreenGridBinding
import com.dhiman.iptv.util.ConstantUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MultiScreenGridActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: MultiScreenGridVM by viewModels()
    private lateinit var binding: ActivityMultiScreenGridBinding
    private var selectedGrid = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_multi_screen_grid
        )
        binding.viewModel = viewModel

        /** Check User Selected Grid */
        checkSelectedGrid()

        /** Click Listeners */
        clickListeners()

        /** Set up onFocus Change Listeners */
        setUpFocusChangeListeners()
    }

    private fun checkSelectedGrid() {
        selectedGrid = sharedPrefs.getInt(ConstantUtil.MULTI_SCREEN_GRID)
        changeSelectedGrid()
    }

    /**
     * Set up onFocus Change Listeners
     */
    private fun setUpFocusChangeListeners() {
        binding.ivBack.requestFocus()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivBack.requestFocus()
        }, 500)
    }


    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            rlFirstGrid.setOnClickListener {
                selectedGrid = 1
                changeSelectedGrid()
            }

            rlSecondGrid.setOnClickListener {
                selectedGrid = 2
                changeSelectedGrid()
            }

            rlThirdGrid.setOnClickListener {
                selectedGrid = 3
                changeSelectedGrid()
            }

            rlFourthGrid.setOnClickListener {
                selectedGrid = 4
                changeSelectedGrid()
            }

            rlFifthGrid.setOnClickListener {
                selectedGrid = 5
                changeSelectedGrid()
            }

            rlSixthGrid.setOnClickListener {
                selectedGrid = 6
                changeSelectedGrid()
            }
        }
    }

    /**
     * Change Selected ImageView as per User Selection
     */
    private fun changeSelectedGrid() {
        binding.apply {
            ivFirstGrid.setImageResource(R.drawable.ic_grid_first)
            ivSecondGrid.setImageResource(R.drawable.ic_grid_second)
            ivThirdGrid.setImageResource(R.drawable.ic_grid_third)
            ivFourthGrid.setImageResource(R.drawable.ic_grid_fourth)
            ivFifthGrid.setImageResource(R.drawable.ic_grid_fifth)
            ivSixthGrid.setImageResource(R.drawable.ic_grid_sixth)

            when (selectedGrid) {
                1 -> {
                    ivFirstGrid.setImageResource(R.drawable.ic_grid_active_first)
                }
                2 -> {
                    ivSecondGrid.setImageResource(R.drawable.ic_grid_active_seond)
                }
                3 -> {
                    ivThirdGrid.setImageResource(R.drawable.ic_grid_active_third)
                }
                4 -> {
                    ivFourthGrid.setImageResource(R.drawable.ic_grid_active_fourth)
                }
                5 -> {
                    ivFifthGrid.setImageResource(R.drawable.ic_grid_active_fifth)
                }
                6 -> {
                    ivSixthGrid.setImageResource(R.drawable.ic_grid_active_sixth)
                }
            }

            sharedPrefs.save(ConstantUtil.MULTI_SCREEN_GRID, selectedGrid)
        }
    }
}