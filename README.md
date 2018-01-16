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
>>>>>>> master
