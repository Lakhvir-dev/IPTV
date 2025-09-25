package com.dhiman.iptv.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.dhiman.iptv.databinding.FragmentExoplayerBinding

class ExoplayerFragment : Fragment(), Player.Listener {

    lateinit var exoPlayer: ExoPlayer
    lateinit var binding: FragmentExoplayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExoplayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /** Setup Exo Player */
        setupExoPlayer()
    }

    /**
     * Setup Exo Player
     */
    private fun setupExoPlayer() {
//        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer.addListener(this)

        binding.spvExoPlayer.player = exoPlayer
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        try {
            Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}