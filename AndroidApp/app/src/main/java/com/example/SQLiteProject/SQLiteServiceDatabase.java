package com.example.SQLiteProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.javaClasses.RequestMaster;

import java.io.Serializable;

public class SQLiteServiceDatabase extends SQLiteDatabaseMaster {
    public SQLiteServiceDatabase(Context context) {
        super(context);
    }

    public enum DATABASE_DATA {
        serviceID,
        name,
        needProofOfResidence,
        needProofOfStatus,
        needPhotoOfUser,
        otherData
    };

    public boolean createNewService(RequestMaster service) {
        db = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("serviceID", service.getID());
            contentValues.put("name", service.getName());
            contentValues.put("needProofOfResidence", service.getPOR());
            contentValues.put("needProofOfStatus", service.getPOS());
            contentValues.put("needPhotoOfUser", service.getPOU());
            contentValues.put("otherData", service.getOtherInformation());

            long result = db.insert(SERVICE_TABLE, null, contentValues);
            if (result==-1) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void createExampleServices() {
        if (!checkExistingService(0)) createNewService(new RequestMaster(0, "Drivers License", true, false, false, "Type of License"));
        if (!checkExistingService(1)) createNewService(new RequestMaster(1, "Health Card", true, true, false));
        if (!checkExistingService(2)) createNewService(new RequestMaster(2, "Photo ID", true, false, true));
    }

    public RequestMaster getService(int ID) {
        if (!checkExistingService(ID)) return null;

        int id = Integer.parseInt((String)getDatabaseData(ID, DATABASE_DATA.serviceID));
        String name = (String)getDatabaseData(ID, DATABASE_DATA.name);
        boolean POR = ((String)(getDatabaseData(ID, DATABASE_DATA.needProofOfResidence))).equals("1");
        boolean POS = ((String)(getDatabaseData(ID, DATABASE_DATA.needProofOfStatus))).equals("1");
        boolean POU = ((String)(getDatabaseData(ID, DATABASE_DATA.needPhotoOfUser))).equals("1");
        String otherData = (String)getDatabaseData(ID, DATABASE_DATA.otherData);

        return new RequestMaster(id, name, POR, POS, POU, otherData);
    }

    private <T> T getDatabaseData(int ID, DATABASE_DATA queryType) {
        if (!checkExistingService(ID)) return null;

        db = this.getWritableDatabase();

        String[] projection;
        switch (queryType) {
            case serviceID:
                projection = new String[]{"serviceID"};
                break;
            case name:
                projection = new String[]{"name"};
                break;
            case needProofOfResidence:
                projection = new String[]{"needProofOfResidence"};
                break;
            case needProofOfStatus:
                projection = new String[]{"needProofOfStatus"};
                break;
            case needPhotoOfUser:
                projection = new String[]{"needPhotoOfUser"};
                break;
            case otherData:
                projection = new String[]{"otherData"};
                break;
            default:
                projection = null;
                break;
        }

        String selection = "serviceID=?";
        String[] selectionArgs = {""+ID};

        Cursor cursor = db.query(SERVICE_TABLE, projection, selection, selectionArgs, null, null, null);
        cursor.moveToNext();
        T returnVariable = (T) cursor.getString(cursor.getColumnIndex(projection[0]));
        return returnVariable;
    }

    public boolean updateService(int ID, RequestMaster service) {
        if (!checkExistingService(ID)) return false;
        try {
            db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("serviceID", ID);
            contentValues.put("name", service.getName());
            contentValues.put("needProofOfResidence", service.getPOR());
            contentValues.put("needProofOfStatus", service.getPOS());
            contentValues.put("needPhotoOfUser", service.getPOU());
            contentValues.put("otherData", service.getOtherInformation());

            db.update(SERVICE_TABLE, contentValues, "serviceID=?", new String[]{"" + ID});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkExistingService(int ID) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from serviceData where serviceID = ? ", new String[]{""+ID});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public String[] getServicesAsList() {
        db = this.getWritableDatabase();

        String[] projection = new String[]{"name"};

        Cursor cursor = db.query(SERVICE_TABLE, projection, null, null, null, null, null);

        String[] serviceNames = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            serviceNames[i] = cursor.getString(cursor.getColumnIndex("name"));
        }
        return serviceNames;
    }
}

