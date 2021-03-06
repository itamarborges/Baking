package com.example.itamarborges.baking;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itamarborges.baking.pojo.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFullDescriptionFragment extends Fragment {

    private static final String POSITION_INDEX = "position";
    private static final String PLAY_WHEN_READY_INDEX = "playWhenReady";


    private Step mStep;

    private OnFragmentInteractionListener mListener;

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    private long position = C.TIME_UNSET;

    @BindView(R.id.step_description)
    TextView stepDescription;

    @BindView(R.id.step_short_description)
    TextView stepShortDescription;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.img_thumbnail)
    ImageView mThumbNail;

    private SimpleExoPlayer mExoPlayer;

    boolean mPlayWhenReady = true;

    public StepFullDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(POSITION_INDEX, position);
        outState.putBoolean(PLAY_WHEN_READY_INDEX, mPlayWhenReady);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_full_description, container, false);
        ButterKnife.bind(this, view);

        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.cooking));

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(POSITION_INDEX, C.TIME_UNSET);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_INDEX, true);
        }
    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
        }
        String userAgent = Util.getUserAgent(getContext(), "Baking");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                new DefaultHttpDataSourceFactory(userAgent),
                new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        if (position != C.TIME_UNSET) {
            mExoPlayer.seekTo(position);
        }

    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }

    public Step getStep() {
        return mStep;
    }

    public void setStep(Step mStep) {
        this.mStep = mStep;
        fillInformation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void fillInformation() {

        if (mExoPlayer != null) {
            mExoPlayer.stop();
        }

        stepShortDescription.setText(mStep.getShortDescription());
        stepDescription.setText(mStep.getDescription());

        if (mStep.getThumbnailURL().isEmpty()) {
            mThumbNail.setVisibility(View.GONE);
        } else {
            mThumbNail.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(mStep.getThumbnailURL())
                    .error(R.drawable.cooking)
                    .placeholder(R.drawable.cooking)
                    .into(mThumbNail);
        }

        String urlPlayer = "";

        if (mStep != null) {
            if (!mStep.getVideoURL().isEmpty()) {
                urlPlayer = mStep.getVideoURL();
            }

            if (!urlPlayer.isEmpty()) {
                mPlayerView.setVisibility(View.VISIBLE);
                initializePlayer(Uri.parse(urlPlayer));
            } else {
                mPlayerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }


}
