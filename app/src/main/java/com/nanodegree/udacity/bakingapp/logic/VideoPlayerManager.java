package com.nanodegree.udacity.bakingapp.logic;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class VideoPlayerManager {

    private static VideoPlayerManager videoPlayerManager;

    private SimpleExoPlayer exoPlayer;

    private boolean isPlaying = false;

    private long currentPosition = 0;

    public static VideoPlayerManager getInstance() {
        if (videoPlayerManager == null) {
            videoPlayerManager = new VideoPlayerManager();
        }
        return videoPlayerManager;
    }

    public void prepare(final Context context, final PlayerView playerView, final String url) {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector(), new DefaultLoadControl());

        final MediaSource mediaSource = buildMediaSource(Uri.parse(url));
        exoPlayer.prepare(mediaSource, true, false);
        exoPlayer.seekTo(currentPosition);
        playerView.setPlayer(exoPlayer);
    }

    public void resetVideoPlayer() {
        currentPosition = 0;
        isPlaying = false;
    }

    @NonNull
    private MediaSource buildMediaSource(@NonNull final Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("BakingApp ExoPlayer")).createMediaSource(uri);
    }

    public void releaseVideoPlayer() {
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        exoPlayer = null;
    }

    public void goToBackground() {
        if (exoPlayer != null) {
            isPlaying = exoPlayer.getPlayWhenReady();
            exoPlayer.setPlayWhenReady(false);
            currentPosition = exoPlayer.getCurrentPosition();
        }
    }

    public void goToForeground() {
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(isPlaying);
        }
    }
}
