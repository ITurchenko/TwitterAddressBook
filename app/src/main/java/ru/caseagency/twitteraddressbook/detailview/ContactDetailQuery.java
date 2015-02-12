package ru.caseagency.twitteraddressbook.detailview;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;

import ru.caseagency.twitteraddressbook.util.Utils;

/**
 * This interface defines constants used by contact retrieval queries.
 */
public interface ContactDetailQuery {
    // A unique query ID to distinguish queries being run by the
    // LoaderManager.
    final static int QUERY_ID = 1;

    // The query projection (columns to fetch from the provider)
    @SuppressLint("InlinedApi")
    final static String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            Utils.hasHoneycomb() ? ContactsContract.Contacts.DISPLAY_NAME_PRIMARY : ContactsContract.Contacts.DISPLAY_NAME,
    };

    // The query column numbers which map to each value in the projection
    final static int ID = 0;
    final static int DISPLAY_NAME = 1;
}
