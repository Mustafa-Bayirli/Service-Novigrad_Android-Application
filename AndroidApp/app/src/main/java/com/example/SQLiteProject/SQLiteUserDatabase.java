package com.example.SQLiteProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * This class contains the methods for pulling information from the SQLite
 * table. It is an extension of SQLiteDatabaseMaster, which stores most of
 * the backend methods used for table management.
 *
 * @author Sophie Vu
 * @author Fred Nguyen
 * @author Grayden Hibbert
 * @author Anthony Polak
 * @author Mustada Bayirli
 */
public class SQLiteUserDatabase extends SQLiteDatabaseMaster {

    //Enum which contains the datatypes for the table
    public enum DATABASE_DATA {
        username,
        password,
        firstName,
        lastName,
        userType,
        branchID
    };

    public void createAdministrator() {
        if (!checkUserExisting("admin", "123admin456")) {
            createNewUser(new String[] {"admin", "123admin456", "Default", "Administrator", "2", "1"});
        }
    }


    /**
     * Constructor for the SQLiteDatabase Accessor
     * @param context
     */
    public SQLiteUserDatabase(Context context) {
        super(context);
    }

    /**
     * Returns true if the username and password exist together in the table
     * @param username username to query
     * @param password password to queru
     * @return true is the username and password exist together
     */
    public boolean checkUserExisting(String username, String password) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from userData where username = ? and password =?", new String[] {username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public boolean checkUsername(String username) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from userData where username = ?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    /**
     * Queries the table for the selected information and returns the
     * data at that index int the table.
     * @param username username to query
     * @param password password to query
     * @param queryType type of data wanted
     * @param <T> type of data getting returned
     * @return data queried from username and password, null if it does not exist
     */
    public <T> T getDatabaseData(String username, String password, DATABASE_DATA queryType) {
        if (!checkUserExisting(username, password)) return null;

        db = this.getWritableDatabase();

        String[] projection;
        switch (queryType) {
            case username:
                projection = new String[]{"username"};
                break;
            case password:
                projection = new String[]{"password"};
                break;
            case firstName:
                projection = new String[]{"firstName"};
                break;
            case lastName:
                projection = new String[]{"lastName"};
                break;
            case userType:
                projection = new String[]{"userType"};
                break;
            case branchID:
                projection = new String[]{"branchID"};
                break;
            default:
                projection = null;
                break;
        }

        String selection = "username=? and password=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(USER_TABLE, projection, selection, selectionArgs, null, null, null);
        cursor.moveToNext();
        T returnVariable = (T) cursor.getString(cursor.getColumnIndex(projection[0]));
        return returnVariable;
    }

    /**
     * Creates a new entry in the database using the data contained in String[] data
     * String data should have the following in the following order:
     * index 0: username
     * index 1: password
     * index 2: first name
     * index 3: last name
     * index 4: userType (0 for customer, 1 for branch employee, and 2 for admin)
     * @param data String contaning the data for the new user
     * @return
     */
    public boolean createNewUser(String[] data) {
        db = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", data[0]);
            contentValues.put("password", data[1]);
            contentValues.put("firstName", data[2]);
            contentValues.put("lastName", data[3]);
            contentValues.put("userType", Integer.parseInt(data[4]));
            contentValues.put("branchID", Integer.parseInt(data[5]));

            long result = db.insert(USER_TABLE, null, contentValues);
            if (result==-1) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Deletes the user if they exist in the table and returns true
     * if the user does not exist or cannot be deleted returns false
     * @param username username for account to delete
     * @return True if account was deleted, false otherwise
     */
    public boolean deleteUser(String username) {
        if (!checkUsername(username)) {
            return false;
        } else {
            try {
                db = this.getWritableDatabase();
                db.delete(USER_TABLE, "username=?", new String[]{username});
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns an array of strings containing each of the usernames of all of those entered in the database
     * @return String[] of usernames
     */
    public String[] getUsersList() {
        db = this.getWritableDatabase();

        String[] projection = new String[]{"username"};

        Cursor cursor = db.query(USER_TABLE, projection, null, null, null, null, null);

        String[] usernames = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            usernames[i] = cursor.getString(cursor.getColumnIndex("username"));
        }
        return usernames;
    }

    /**
     * Returns the password of the given username (used in admin function)
     * @param username
     * @return
     */
    public String getPassword(String username) {
        if (!checkUsername(username)) return null;

        db = this.getWritableDatabase();

        String[] projection = new String[]{"password"};


        String selection = "username=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(USER_TABLE, projection, selection, selectionArgs, null, null, null);
        cursor.moveToNext();
        String password = cursor.getString(cursor.getColumnIndex(projection[0]));
        return password;
    }
}
