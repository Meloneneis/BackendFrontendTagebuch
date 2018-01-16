<<<<<<< HEAD
# BackendTagebuch

SQLite ArrayListAdapter CustomAdapter
=======
# BackendTagebuch

openOrCreateDatabase 
query = "CREATE TABLE entries ( id INTEGER PRIMARY KEY, date TEXT, content TEXT)"; <br>
ContentValues values = new ContentValues(); <br>
values.put("Name", queryValues.get("name")); <br>
database.insert("entries", null, values); <br>
database.close();

<TextView
        android:id="@+id/titletv"
        android:layout_width="150dp"
        android:layout_height="40dp"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginLeft="15dp"
        android:text="TextView"
        android:textSize="25dp" />

    <TextView
        android:id="@+id/datetv"
        android:layout_width="100dp"
        android:layout_height="25dp"
        android:layout_alignBottom="@+id/titletv"
        android:layout_alignParentEnd="true"
        android:layout_marginRight="15dp"
        android:text="TextView" />
>>>>>>> master
