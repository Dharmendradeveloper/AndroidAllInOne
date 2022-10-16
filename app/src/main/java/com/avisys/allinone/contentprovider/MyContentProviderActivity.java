package com.avisys.allinone.contentprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;

import com.avisys.allinone.R;

public class MyContentProviderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView listView;
    private ContentResolver contentResolver;
    private static final String TAG = MyContentProviderActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_content_provier);
        init();
    }

    private void init(){
        listView = findViewById(R.id.list_view);
        contentResolver = getContentResolver();// Object
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,
                null,null);
//        getLoaderManager().initLoader(0,null,this);
        setSimpleCursorAdapter(cursor);

    }

    private void setSimpleCursorAdapter(Cursor cursor){
            String[] from = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};
            int[] to = new int[]{R.id.contact_name,R.id.contact_number};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.content_provider_simple_cursor_adapter, cursor,from,to);
            listView.setAdapter(adapter);
        }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}