package com.dhiman.iptv.fragment.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dhiman.iptv.databinding.FragmentSpeedTestBinding
import com.dhiman.iptv.fragment.setting.general_settings.GeneralSettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpeedTestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeedTestFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var binding: FragmentSpeedTestBinding? = null
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: GeneralSettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSpeedTestBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel=viewModel
        startTest()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpeedTestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SpeedTestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun startTest() {
        binding?.btnSpeedTest?.setOnClickListener {
            // Hide button when test starts
            binding?.btnSpeedTest?.visibility = View.GONE

            // Use viewLifecycleOwner.lifecycleScope for fragments
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    // The testDownloadSpeed function will now handle the background thread
                    val speed = testDownloadSpeed("https://speed.hetzner.de/100MB.bin")

                    // Update the UI back on the main thread
                    binding?.speedView?.speedTo(speed.toFloat())
                    Log.d("SppedTesting", "startTest: "+ String.format("%.2f Mbps", speed))

                } catch (e: Exception) {
                    e.printStackTrace()
                    // Optionally show an error message to the user
                } finally {
                    // This will also run on the main thread
                    binding?.btnSpeedTest?.visibility = View.VISIBLE
                }
            }
        }
    }

    // Make the function run on a background thread using withContext
    suspend fun testDownloadSpeed(url: String): Double = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val connection = URL(url).openConnection()
        connection.connect()
        val input = connection.getInputStream()
        val buffer = ByteArray(1024)
        var totalBytes = 0L
        var bytesRead: Int

        while (input.read(buffer).also { bytesRead = it } != -1) {
            totalBytes += bytesRead
        }

        input.close() // Don't forget to close the stream

        val endTime = System.currentTimeMillis()

        // Avoid division by zero if the download is instant
        if (endTime == startTime) return@withContext 0.0

        val timeTakenSec = (endTime - startTime) / 1000.0
        val bitsPerSecond = (totalBytes * 8) / timeTakenSec
        return@withContext bitsPerSecond / (1024 * 1024) // Mbps
    }


}