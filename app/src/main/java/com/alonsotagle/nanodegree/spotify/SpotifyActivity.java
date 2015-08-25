package com.alonsotagle.nanodegree.spotify;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.alonsotagle.nanodegree.Application;
import com.alonsotagle.nanodegree.R;
import com.alonsotagle.nanodegree.spotify2.SpotifyPlayerActivity;
import com.alonsotagle.nanodegree.spotify2.SpotifyPlayerFragment;

public class SpotifyActivity extends AppCompatActivity implements SpotifySearcherFragment.ArtistSelectedListener{

    private SpotifyTopTracksActivityFragment spotifyTopTracksActivityFragment;
    private boolean isTablet;
    private MenuItem mPlayingNow;
    private AnimationDrawable mPlayNowAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            if (savedInstanceState == null) {
                spotifyTopTracksActivityFragment = new SpotifyTopTracksActivityFragment();
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.spotify_top_ten_container, spotifyTopTracksActivityFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        boolean isPlayingNow = Application.getIsPlayingNow();

        if(hasFocus && isPlayingNow && mPlayingNow != null) {
            mPlayingNow.setVisible(true);
            mPlayNowAnimationDrawable.start();
        } else {
            mPlayingNow.setVisible(false);
            mPlayNowAnimationDrawable.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spotify, menu);

        mPlayingNow = menu.findItem(R.id.action_playing_now);
        mPlayNowAnimationDrawable = (AnimationDrawable) mPlayingNow.getIcon();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_playing_now) {


            if (isTablet) {
                SpotifyPlayerFragment spotifyPlayerFragment = new SpotifyPlayerFragment();
                spotifyPlayerFragment.setTwoPane(true);
                spotifyPlayerFragment.show(this.getFragmentManager(), "spotify_player");
            } else {
                Intent playerIntent = new Intent(this, SpotifyPlayerActivity.class);
                startActivity(playerIntent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchArtistTopTracks(String artistId) {
        if(isTablet) {
            this.spotifyTopTracksActivityFragment.showArtistTopTrack(artistId);
        } else {
            Intent topArtistSongsIntent = new Intent(SpotifyActivity.this, SpotifyTopTracksActivity.class);
            topArtistSongsIntent.putExtra("artist_id", artistId);
            startActivity(topArtistSongsIntent);
        }
    }
}
