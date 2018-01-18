package com.example.niklas.backendtagebuch.adapter.listview;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.niklas.backendtagebuch.R;
import com.example.niklas.backendtagebuch.database.EntryDatabase;
import com.example.niklas.backendtagebuch.model.Entry;

import java.util.Calendar;
import java.util.List;

public class EntryOverviewListAdapter extends CursorAdapter  {

    public EntryOverviewListAdapter(final Context context, final Cursor cursor){
        super(context,cursor,0);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.entry_overview_listitem, viewGroup , false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.titletv)).setText(cursor.getString(cursor.getColumnIndex(EntryDatabase.TITLE_COLUMN)));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(cursor.getInt(cursor.getColumnIndex(EntryDatabase.DATE_COLUMN))*1000);
        TextView date = (TextView) view.findViewById(R.id.datetv);
        date.setText(String.valueOf(calendar.get(Calendar.YEAR)));
    }

    /*
    public EntryOverviewListAdapter(final Context context, final List<Entry> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView (final int postion, final View convertView, final ViewGroup parent) {
        Entry currententry = getItem(postion);
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.entry_overview_listitem, parent, false);

        }
        ((TextView) view.findViewById(R.id.titletv)).setText(currententry.getTitle());
        TextView date = (TextView) view.findViewById(R.id.datetv);
        date.setText(String.valueOf(currententry.getDate().get(Calendar.YEAR)));
        return view;
    }

    */
}