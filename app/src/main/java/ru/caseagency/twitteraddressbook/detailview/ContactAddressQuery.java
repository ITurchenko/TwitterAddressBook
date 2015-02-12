package ru.caseagency.twitteraddressbook.detailview;

import android.provider.ContactsContract;

/**
 * This interface defines constants used by address retrieval queries.
 */
public interface ContactAddressQuery {
    // A unique query ID to distinguish queries being run by the
    // LoaderManager.
    final static int QUERY_ID = 2;

    // The query projection (columns to fetch from the provider)
    final static String[] PROJECTION = {
            ContactsContract.CommonDataKinds.StructuredPostal._ID,
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.LABEL,
    };

    // The query selection criteria. In this case matching against the
    // StructuredPostal content mime type.
    final static String SELECTION =
            ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE + "'";

    // The query column numbers which map to each value in the projection
    final static int ID = 0;
    final static int ADDRESS = 1;
    final static int TYPE = 2;
    final static int LABEL = 3;
}
