package ru.caseagency.twitteraddressbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;

public class TwitterManagerFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {

    private final int TWITTER = 1;

    private SocialNetworkManager mSocialNetworkManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.empty, container, false);

        Log.e("AA","create TwManager");

        init();

        return rootView;
    }

    public void init() {
        mSocialNetworkManager = new SocialNetworkManager();

        TwitterSocialNetwork twNetwork = new TwitterSocialNetwork(this, "LKFmQe7nwVOikPAohwd5OlkIR", "33HlpPQDRKEVL3fZbrk2nM173L1k4v85903YooZQ5ITJ0neysk", "\"oauth://ASNE\"");
        mSocialNetworkManager.addSocialNetwork(twNetwork);
    }

    private void initSocialNetwork(SocialNetwork socialNetwork){
        if(socialNetwork.isConnected()){
            switch (socialNetwork.getID()){
                case TwitterSocialNetwork.ID:
                    Log.e("AA","Twitter initted");
                    break;
            }
        }
    }
    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    @Override
    public void onLoginSuccess(int networkId) {
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
