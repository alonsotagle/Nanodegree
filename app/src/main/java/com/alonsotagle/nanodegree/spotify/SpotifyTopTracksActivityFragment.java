package com.alonsotagle.nanodegree.spotify;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alonsotagle.nanodegree.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpotifyTopTracksActivityFragment extends Fragment {

    private ArtistTracksAdapter artistTracksAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistTracksAdapter = new ArtistTracksAdapter();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spotify_fragment_top_tracks, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView artistTracksResults = (ListView)view.findViewById(R.id.lv_spotify_songs_results);
        artistTracksResults.setAdapter(artistTracksAdapter);
    }

    public void showArtistTopTrack(String artistId) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.spotify_dialog_wait));

        SpotifyApi spotifyApi = new SpotifyApi();
        SpotifyService spotifyService = spotifyApi.getService();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("country", Locale.getDefault().getCountry());
        spotifyService.getArtistTopTrack(artistId, parameters, new Callback<Tracks>() {
            @Override
            public void success(final Tracks tracks, Response response) {

                if (response.getStatus() == 200) {
                    if (tracks != null && getActivity() != null) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                artistTracksAdapter.setItems(tracks.tracks);
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                artistTracksAdapter.clearData();
                                progressDialog.dismiss();

                            }
                        });

                    }
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
}
