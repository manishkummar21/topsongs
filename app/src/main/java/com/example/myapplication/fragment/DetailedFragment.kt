package com.example.myapplication.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainViewModel
import com.example.myapplication.PlayToPause
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailedBinding
import com.example.myapplication.helper.Utils.convertSecondsToMmSs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailedFragment : Fragment(), Player.EventListener {

    private var _binding: FragmentDetailedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main)

    private var currentPosition: Long = -1L

    private var prevSeekPosition: Long = -1L

    private var player: SimpleExoPlayer? = null

    private val dataSourceFactory by lazy {
        DefaultDataSourceFactory(requireContext(), "exoplayer-sample")
    }

    private val clickedItem by lazy {
        viewModel.clickedItem.value
    }

    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null)
            _binding = FragmentDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickedItem?.let {
            binding.song = it
            binding.seekposition.text = getString(R.string.seek_pos)
        }

        binding.close.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.play.setOnClickListener {
            binding.playView.findViewById<ImageButton>(R.id.exo_play).performClick()
            toggleController(PlayToPause.PlayToPause)
        }

        binding.pause.setOnClickListener {
            binding.playView.findViewById<ImageButton>(R.id.exo_pause).performClick()
            toggleController(PlayToPause.PauseToPlay)
        }
    }

    private fun initializePlayer() {
        player?.let {
            it.playWhenReady = true
            it.addListener(this)
            it.setMediaSource(buildMediaSource(binding.song?.song?.audioLink))
            it.prepare()
            binding.playView.player = it
            binding.playView.requestFocus()

        } ?: let {
            player = SimpleExoPlayer.Builder(requireContext()).build()
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        initializePlayer()
    }


    override fun onPause() {
        super.onPause()
        stopProgressListener()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.let {
            it.removeListener(this)
            it.release()
            player = null
            toggleController(PlayToPause.PauseToPlay)
            binding.controlBar.visibility = View.GONE
        }
    }

    private fun buildMediaSource(link: String?): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(link)))
    }

    override fun onPlaybackStateChanged(state: Int) {
        super.onPlaybackStateChanged(state)
        when (state) {
            Player.STATE_READY -> {
                seekToPosition()
                showControllerOnReady()
                CalculateDuration()
            }
            Player.STATE_ENDED -> {
                toggleController(PlayToPause.PauseToPlay)
            }
            Player.STATE_IDLE, Player.STATE_BUFFERING -> {
            }
        }
    }

    private fun showControllerOnReady() {
        toggleController(PlayToPause.PlayToPause)
        binding.controlBar.visibility = View.VISIBLE
    }

    private fun CalculateDuration() {
        player?.let {
            binding.totalDuration.text = convertSecondsToMmSs(it.duration)
            binding.seekposition.text = convertSecondsToMmSs(it.currentPosition)
            startProgressListener(it)
        }
    }

    private fun seekToPosition() {
        player?.let {
            if (currentPosition != -1L && (currentPosition != prevSeekPosition)) {
                prevSeekPosition = currentPosition
                it.seekTo(currentPosition)
            }
        }

    }


    private fun toggleController(pausePlay: PlayToPause) {
        when (pausePlay) {
            PlayToPause.PlayToPause -> {
                binding.play.visibility = View.GONE
                binding.pause.visibility = View.VISIBLE
            }
            PlayToPause.PauseToPlay -> {
                binding.pause.visibility = View.GONE
                binding.play.visibility = View.VISIBLE
            }
        }
    }

    private fun startProgressListener(player: SimpleExoPlayer) {
        if (job != null)
            return

        job = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                val currentPosition = player.currentPosition
                //distinct untill changed
                if (this@DetailedFragment.currentPosition != currentPosition) {
                    this@DetailedFragment.currentPosition = currentPosition
                }
                binding.seekposition.text = convertSecondsToMmSs(currentPosition)

                //primary progress
                val progressed: Int = ((currentPosition * 100) / player.duration).toInt()
                binding.progressPlaceholder.progress = progressed

                //buffered progress
                val buffered: Int = ((player.bufferedPosition * 100) / player.duration).toInt()
                binding.progressPlaceholder.secondaryProgress = buffered

                delay(1000)
            }
        }
    }


    private fun stopProgressListener() {
        job?.cancel()
        job = null
    }
}