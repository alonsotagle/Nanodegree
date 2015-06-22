package com.alonsotagle.nanodegree.spotify;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alonsotagle.nanodegree.R;
import com.alonsotagle.nanodegree.utils.Utils;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class SpotifySearcherFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ArtistSearchAdapter artistSearchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        artistSearchAdapter = new ArtistSearchAdapter();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spotify_fragment_search, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView artistSearchResults = (ListView)view.findViewById(R.id.lv_spotify_artist_results);
        artistSearchResults.setAdapter(artistSearchAdapter);
        artistSearchResults.setOnItemClickListener(this);

        final EditText etSearchArtist = (EditText) view.findViewById(R.id.et_spotify_search);
        etSearchArtist.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                Utils.hideKeyBoard(view);
                startArtistSearch(view.getContext(), etSearchArtist.getText().toString());
                return true;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void startArtistSearch(final Context context, String artist) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.spotify_dialog_wait));

        SpotifyApi spotifyApi = new SpotifyApi();
        SpotifyService spotifyService = spotifyApi.getService();

        spotifyService.searchArtists(artist, new Callback<ArtistsPager>() {
            @Override
            public void success(final ArtistsPager artistsPager, Response response) {

                if (response.getStatus() == 200) {
                    if (artistsPager != null && getActivity() != null) {
                        if (artistsPager.artists.items.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    artistSearchAdapter.setItems(artistsPager.artists.items);
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    artistSearchAdapter.clearData();
                                    progressDialog.dismiss();
                                    Utils.showToast(context, getString(R.string.spotify_searcher_not_found), Toast.LENGTH_LONG);
                                }
                            });
                        }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Artist artist = (Artist) adapterView.getAdapter().getItem(i);
        Intent artistSongsIntent = new Intent(getActivity(), SpotifyTopTracksActivity.class);
        artistSongsIntent.putExtra("artist_id", artist.id);
        artistSongsIntent.putExtra("artist_name", artist.name);
        startActivity(artistSongsIntent);
    }
}
