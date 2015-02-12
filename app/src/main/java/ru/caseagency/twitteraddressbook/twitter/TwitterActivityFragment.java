package ru.caseagency.twitteraddressbook.twitter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestGetFriendsCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.twitter.TwitterSocialNetwork;

import java.util.ArrayList;

import ru.caseagency.twitteraddressbook.R;

public class TwitterActivityFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {

    public static final String CONSUMER_KEY = "LKFmQe7nwVOikPAohwd5OlkIR";
    public static final String CONSUMER_SECRET = "33HlpPQDRKEVL3fZbrk2nM173L1k4v85903YooZQ5ITJ0neysk";
    public static final String REDIRECT_URL = "oauth://ASNE";

    private final int TWITTER = 1;

    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";

    private SocialNetworkManager mSocialNetworkManager;
    private ContentAdapter contentAdapter;
    private View loader;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitter_fragment, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.twitter_list);
        contentAdapter = new ContentAdapter(this);
        listView.setAdapter(contentAdapter);

        loader = rootView.findViewById(R.id.loader);

        init();

        loginBySocial(TWITTER);

        return rootView;
    }

    public void init() {
        mSocialNetworkManager = new SocialNetworkManager();

        TwitterSocialNetwork twNetwork = new TwitterSocialNetwork(this, CONSUMER_KEY, CONSUMER_SECRET, REDIRECT_URL);
        mSocialNetworkManager.addSocialNetwork(twNetwork);

        getFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();
        mSocialNetworkManager.setOnInitializationCompleteListener(this);
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
        }
    }

    private void loginBySocial(int networkID) {
        try {
            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkID);

            if (socialNetwork.isConnected()) {
//                socialNetwork.logout();
                onLoginSuccess(socialNetwork.getID());
            } else {
                socialNetwork.setOnLoginCompleteListener(this);
                socialNetwork.requestLogin();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoginSuccess(int networkId) {
        SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);

        socialNetwork.requestGetFriends(new OnRequestGetFriendsCompleteListener() {
            @Override
            public void OnGetFriendsIdComplete(int i, String[] strings) {
            }

            @Override
            public void OnGetFriendsComplete(int i, ArrayList<SocialPerson> socialPersons) {
                hideLoader();
                for (SocialPerson socialPerson : socialPersons) {
                    contentAdapter.add(socialPerson);
                }
            }

            @Override
            public void onError(int i, String s, String s2, Object o) {
                hideLoader();
                Toast.makeText(getActivity(), "ERROR: " + s, Toast.LENGTH_LONG).show();}
        });
    }

    private void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
