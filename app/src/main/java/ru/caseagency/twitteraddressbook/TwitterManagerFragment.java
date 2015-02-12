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
import com.github.gorbin.asne.core.listener.OnRequestDetailedSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;

public class TwitterManagerFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener, OnRequestDetailedSocialPersonCompleteListener {

    public static final String CONSUMER_KEY = "LKFmQe7nwVOikPAohwd5OlkIR";
    public static final String CONSUMER_SECRET = "33HlpPQDRKEVL3fZbrk2nM173L1k4v85903YooZQ5ITJ0neysk";
    public static final String REDIRECT_URL = "oauth://ASNE";

    private final int TWITTER = 1;

    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";

    private SocialNetworkManager mSocialNetworkManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.empty, container, false);

        Log.e("AA","create TwManager");

        init();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginBySocial(TWITTER);
    }

    public void init() {
        mSocialNetworkManager = new SocialNetworkManager();

        TwitterSocialNetwork twNetwork = new TwitterSocialNetwork(this, CONSUMER_KEY, CONSUMER_SECRET, REDIRECT_URL);
        mSocialNetworkManager.addSocialNetwork(twNetwork);

        getFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();
        mSocialNetworkManager.setOnInitializationCompleteListener(this);
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
        Log.e("AA","OnSM manager inited");
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    private void loginBySocial(int networkID) {
        try {
            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkID);

            Log.e("AA","Login by social "+networkID+", isConnected="+socialNetwork.isConnected());

            if (socialNetwork.isConnected()) {
//                socialNetwork.logout();
                onLoginSuccess(socialNetwork.getID());
            } else {
                socialNetwork.setOnLoginCompleteListener(this);
                socialNetwork.requestLogin();
            }
        } catch (Exception e) {
            Log.e("AA", "Some social error: " + e.getMessage());
        }
    }

    @Override
    public void onLoginSuccess(int networkId) {
        Log.e("AA","OnLogin success to "+networkId);

        SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);

        socialNetwork.setOnRequestDetailedSocialPersonCompleteListener(this);
        socialNetwork.requestDetailedCurrentPerson();
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        Log.e("AA","Error -> "+errorMessage+" :: "+requestID);
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestDetailedSocialPersonSuccess(int i, SocialPerson socialPerson) {
        Log.e("AA", "Get person "+socialPerson);
    }
}
