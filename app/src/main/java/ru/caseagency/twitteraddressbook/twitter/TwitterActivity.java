package ru.caseagency.twitteraddressbook.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import ru.caseagency.twitteraddressbook.R;

public class TwitterActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_twitter);

        setTitle(getString(R.string.twitter_title));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TwitterActivityFragment.SOCIAL_NETWORK_TAG);
        if (fragment != null && data != null) {
            try {
                fragment.onActivityResult(requestCode, resultCode, data);
            } catch (Exception e) {
                Log.e(getClass().getName(), "Some social network error: " + e.getMessage());
            }
        }
    }
}
