# BackendTagebuch

openOrCreateDatabase 
query = "CREATE TABLE entries ( id INTEGER PRIMARY KEY, date TEXT, content TEXT)";
ContentValues values = new ContentValues(); <br>
values.put("Name", queryValues.get("name")); <br>
database.insert("entries", null, values); <br>
database.close(); 
