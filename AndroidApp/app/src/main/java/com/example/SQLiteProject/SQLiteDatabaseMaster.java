package com.example.SQLiteProject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class contains the backend code for the SQlite database
 * It holds all of the information and has the ability to call the
 * database when it is needed.
 *
 * @author Sophie Vu
 * @author Grayden Hibbert
 * @author Anthony Polak
 * @author Mustada Bayirli
 */
public class SQLiteDatabaseMaster extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "Service_Novigrad.db";
    protected static final int DATABASE_VERSION = 1;
    protected final Context context;
    SQLiteDatabase db;

    protected static String USER_TABLE = "userData";
    protected static String BRANCH_TABLE = "branchData";
    protected static String SERVICE_TABLE = "serviceData";
    protected static String REQUEST_TABLE = "requestData";

    protected static final String DATABASE_PATH = "/data/data/com.example.project_notgoingtofail/databases";

    public String DatabaseName(){
        return DATABASE_PATH;
    }

    /***
     * calls the constuctor for SQLiteDatabaseMaster
     * @param context
     */
    public SQLiteDatabaseMaster(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //createDb();
    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table userData(username TEXT primary key, password TEXT, firstName TEXT, lastName TEXT, userType INTEGER, branchID INTEGER)");
        db.execSQL("create Table branchData(branchID INTEGER primary key, name TEXT, openingHours TEXT, closingHours TEXT, address TEXT, phoneNumber TEXT, servicesProvided TEXT)");
        db.execSQL("create Table serviceData(serviceID INTEGER primary key, name TEXT, needProofOfResidence BOOL, needProofOfStatus BOOL, needPhotoOfUser BOOL, otherData TEXT)");
        db.execSQL("create Table requestData(requestID INTEGER, firstName TEXT, lastName TEXT, address TEXT, dateOfBirth TEXT, proofOfResidence BLOB, proofOfStatus BLOB, photoOfUser BLOB, otherData TEXT)");
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userData");
    }

    /**
     * Creates the database if it does not exist
     */
    protected void createDb() {
        boolean dbExist = checkDbExist();

        if (!dbExist) {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    /**
     * Returns true if the given database exists
     * @return true if the database exists, false otherwise
     */
    protected boolean checkDbExist() {
        SQLiteDatabase dataBase = null;

        try {
            String path = DATABASE_PATH + DATABASE_NAME;
            dataBase = SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {}

        if (dataBase != null) {
            dataBase.close();
            return true;
        }
        return false;
    }

    /***
     * Copies the database given from assets into a pathed location that exists on the phone
     */
    protected void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;

            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Opens the database and stores it in the db and return db
     * @return db which stores the database
     */
    protected SQLiteDatabase openDatabase() {
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    /***
     * closes db if it is not null
     */
    public void close() {
        if (db != null) {
            db.close();
        }
    }
}
