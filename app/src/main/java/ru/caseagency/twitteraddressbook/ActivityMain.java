package ru.caseagency.twitteraddressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ru.caseagency.twitteraddressbook.addressbook.ContactsListActivity;
import ru.caseagency.twitteraddressbook.twitter.TwitterActivity;

public class ActivityMain extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_addresses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ContactsListActivity.class));
            }
        });
        findViewById(R.id.btn_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, TwitterActivity.class));
            }
        });
    }
}
