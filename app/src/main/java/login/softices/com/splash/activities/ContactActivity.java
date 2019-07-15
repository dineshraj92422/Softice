package login.softices.com.splash.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;

import login.softices.com.splash.R;
import login.softices.com.splash.adapter.ContactListAdapter;

public class ContactActivity extends AppCompatActivity {
    private ContactListAdapter adapter;
    private ListView listView;
    private final int REQUEST_CODE=1;
    private ArrayList<String> Name_ArrayList = new ArrayList<String>();
    private ArrayList<String> Number_ArrayList = new ArrayList<String>();
    private String TAG = "ContactActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();
    }

        private void init() {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            listView = findViewById(R.id.list_contact);
            checkPermission();
        }



    private void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            retrieveContacts();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission
                    .READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission
                    .WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                retrieveContacts();
            } else {
                ActivityCompat.requestPermissions(ContactActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS,
                                Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE);
            }
        }
    }

    /** \
     * Request for permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_CODE:
                    goWithContacsPermission(grantResults);
                    break;
                default:
                    break;
            }
        }
    }

    /** \
     * Request for contacts permission
     * @param grantResults
     */
    private void goWithContacsPermission(int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            retrieveContacts();
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ContactActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE);
        }
    }

    /**
     * \
     * Retrives the contact list on phone.
     */
    private void retrieveContacts() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (phones != null && phones.moveToFirst()) {
            do {
                Name_ArrayList.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                Number_ArrayList.add(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            } while (phones.moveToNext());
        }
        adapter = new ContactListAdapter(ContactActivity.this, Name_ArrayList, Number_ArrayList);
        listView.setAdapter(adapter);
        phones.close();
    }

    /** \
     * This method functions when clicked on toolbar items
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
