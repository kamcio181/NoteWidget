package com.apps.home.notewidget.widget;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.apps.home.notewidget.R;
import com.apps.home.notewidget.utils.Constants;
import com.apps.home.notewidget.utils.Utils;

public class WidgetConfigActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    private int widgetID = 0;
    private ListView notesListView;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int noteId;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_widget_config);
        setResult(RESULT_CANCELED);

        Utils.hideShadowSinceLollipop(this);

        notesListView = (ListView) findViewById(R.id.notesListView);
        new LoadNotes().execute();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            Log.e("config onCreate", "widgetId "+ widgetID);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        cursor.moveToPosition(i);
        noteId = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.ID_COL));
        insertOrUpdateItem();

        getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE).edit().
                putBoolean(widgetID + Constants.CONFIGURED_KEY, true).commit();

        WidgetProvider widgetProvider = new WidgetProvider();
        widgetProvider.onUpdate(this, AppWidgetManager.getInstance(this), new int[]{widgetID});


        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void insertOrUpdateItem() {
        Log.e("config", "insert");
        Cursor cursor = db.query(Constants.WIDGETS_TABLE, new String[]{Constants.ID_COL},
                Constants.WIDGET_ID_COL + " = ?", new String[]{Integer.toString(widgetID)}, null, null, null, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.CONNECTED_NOTE_ID_COL, noteId);

        if(cursor.getCount()>0) {

            cursor.close();
            db.update(Constants.WIDGETS_TABLE, contentValues, Constants.WIDGET_ID_COL + " = ?",
                    new String[]{Integer.toString(widgetID)});
            Log.e("config", "item updated " + contentValues.toString());
        } else {
            contentValues.put(Constants.WIDGET_ID_COL, widgetID);
            contentValues.put(Constants.CURRENT_WIDGET_MODE_COL, Constants.WIDGET_MODE_TITLE);
            contentValues.put(Constants.CURRENT_THEME_MODE_COL, Constants.WIDGET_THEME_LIGHT);
            contentValues.put(Constants.CURRENT_TEXT_SIZE_COL, 13);
            Log.e("config", "item inserted " + contentValues.toString());

            db.insert(Constants.WIDGETS_TABLE, null, contentValues);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class LoadNotes extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            if((db = Utils.getDb(WidgetConfigActivity.this)) != null) {

                cursor = db.query(Constants.NOTES_TABLE, new String[]{Constants.ID_COL, Constants.NOTE_TITLE_COL},
                        Constants.DELETED_COL + " = ?", new String[]{"0"}, null, null, Constants.NOTE_TITLE_COL + " ASC");
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                cursor.moveToFirst();
                notesListView.setAdapter(new SimpleCursorAdapter(WidgetConfigActivity.this,
                        android.R.layout.simple_list_item_1, cursor,
                        new String[]{Constants.NOTE_TITLE_COL}, new int[]{android.R.id.text1}, 0));
                notesListView.setOnItemClickListener(WidgetConfigActivity.this);
            }
            else {
                finish();
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(cursor!=null && !cursor.isClosed())
            cursor.close();
        Utils.closeDb();
    }
}
