package ru.caseagency.twitteraddressbook.addressbook;

import android.net.Uri;

/**
 * This interface must be implemented by any activity that loads this fragment. When an
 * interaction occurs, such as touching an item from the ListView, these callbacks will
 * be invoked to communicate the event back to the activity.
 */
public interface OnContactsInteractionListener {
    /**
     * Called when a contact is selected from the ListView.
     * @param contactUri The contact Uri.
     */
    public void onContactSelected(Uri contactUri);

    /**
     * Called when the ListView selection is cleared like when
     * a contact search is taking place or is finishing.
     */
    public void onSelectionCleared();
}
