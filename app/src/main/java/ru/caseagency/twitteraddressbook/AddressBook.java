package ru.caseagency.twitteraddressbook;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class AddressBook extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address_book);

        getContactList();
    }

    private void getContactList() {
        Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String aNameFromContacts[] = new String[contacts.getCount()];
        String aNumberFromContacts[] = new String[contacts.getCount()];
        int i = 0;

        int nameFieldColumnIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
        int numberFieldColumnIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.NUMBER);

        while(contacts.moveToNext()) {

            String contactName = contacts.getString(nameFieldColumnIndex);
//            aNameFromContacts[i] =    contactName ;

            String number = contacts.getString(numberFieldColumnIndex);
//            aNumberFromContacts[i] =    number ;

            Log.e("AA"," -> "+contactName+" :: "+number);
            i++;
        }

        contacts.close();
    }
}
