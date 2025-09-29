package com.example.SQLiteProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SQLiteBranchDatabase extends SQLiteDatabaseMaster {
    public SQLiteBranchDatabase(Context context) {
        super(context);
    }

    public enum DATABASE_DATA {
        branchID,
        name,
        openingHours,
        closingHours,
        address,
        phoneNumber,
        servicesProvided
    };

    public void createBaseBranch() {
        if (!checkBranchExisting(0)) {
            createNewBranch(0, "Customer branch");
        } if (!checkBranchExisting(1)) {
            createNewBranch(1, "Administrator Branch");
        }
    }


    public boolean createNewBranch(int ID, String name) {
        db = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("branchID", ID);
            contentValues.put("name", name);
            contentValues.put("openingHours" ,"12:00,10:00,10:00,10:00,10:00,10:00,10:00");
            contentValues.put("closingHours" ,"18:00,21:00,21:00,21:00,21:00,21:00,21:00");
            contentValues.put("servicesProvided", "");
            contentValues.put("address", "Temporary Address");
            contentValues.put("phoneNumber", "555 555 5555");

            long result = db.insert(BRANCH_TABLE, null, contentValues);
            if (result==-1) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateBranch(int ID, String name) {
        if (!checkBranchExisting(ID)) return false;
        try {
            db = this.getWritableDatabase();



            ContentValues contentValues = new ContentValues();
            contentValues.put("branchID", ID);
            contentValues.put("name", name);
            contentValues.put("openingHours", getDatabaseData(ID, DATABASE_DATA.openingHours).toString());
            contentValues.put("closingHours", getDatabaseData(ID, DATABASE_DATA.closingHours).toString());
            contentValues.put("servicesProvided", getDatabaseData(ID, DATABASE_DATA.servicesProvided).toString());
            contentValues.put("address", getDatabaseData(ID, DATABASE_DATA.address).toString());
            contentValues.put("phoneNumber", getDatabaseData(ID, DATABASE_DATA.phoneNumber).toString());

            db.update(BRANCH_TABLE, contentValues, "branchID=?", new String[]{"" + ID});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkBranchExisting(int ID) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from branchData where branchID = ?", new String[] {""+ID});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public <T> T getDatabaseData(int ID, SQLiteBranchDatabase.DATABASE_DATA queryType) {
        if (!checkBranchExisting(ID)) return null;

        db = this.getWritableDatabase();

        String[] projection;
        switch (queryType) {
            case branchID:
                projection = new String[]{"branchID"};
                break;
            case name:
                projection = new String[]{"name"};
                break;
            case openingHours:
                projection = new String[]{"openingHours"};
                break;
            case closingHours:
                projection = new String[]{"closingHours"};
                break;
            case address:
                projection = new String[]{"address"};
                break;
            case phoneNumber:
                projection = new String[]{"phoneNumber"};
                break;
            case servicesProvided:
                projection = new String[]{"servicesProvided"};
                break;
            default:
                projection = null;
                break;
        }

        String selection = "branchID=?";
        String[] selectionArgs = {""+ID};

        Cursor cursor = db.query(BRANCH_TABLE, projection, selection, selectionArgs, null, null, null);
        cursor.moveToNext();
        T returnVariable = (T) cursor.getString(cursor.getColumnIndex(projection[0]));
        return returnVariable;
    }

    public String[] getBranchAsList() {
        db = this.getWritableDatabase();

        String[] projection = new String[]{"name"};

        Cursor cursor = db.query(BRANCH_TABLE, projection, null, null, null, null, null);

        String[] branchNames = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            branchNames[i] = cursor.getString(cursor.getColumnIndex("name"));
        }
        return branchNames;
    }

    public boolean deleteBranch(int ID) {
        if (!checkBranchExisting(ID)) {
            return false;
        } else {
            try {
                db = this.getWritableDatabase();
                db.delete(BRANCH_TABLE, "branchID=?", new String[]{""+ID});
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public boolean updateWorkingHours(int ID, String[] open, String[] close) {
        if (!checkBranchExisting(ID)) return false;
        try {
            db = this.getWritableDatabase();

            String newOpen = "";
            String newClose = "";

            for (int i = 0; i < open.length; i++) {
                newOpen += open[i] + ",";
                newClose += close[i] + ",";
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("branchID", ID);
            contentValues.put("name", getDatabaseData(ID, DATABASE_DATA.name).toString());
            contentValues.put("openingHours", newOpen.substring(0, newOpen.length()-1));
            contentValues.put("closingHours", newClose.substring(0, newClose.length()-1));
            contentValues.put("servicesProvided", getDatabaseData(ID, DATABASE_DATA.servicesProvided).toString());
            contentValues.put("address", getDatabaseData(ID, DATABASE_DATA.address).toString());
            contentValues.put("phoneNumber", getDatabaseData(ID, DATABASE_DATA.phoneNumber).toString());

            db.update(BRANCH_TABLE, contentValues, "branchID=?", new String[]{"" + ID});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateServices(int ID, String[] serviceIDS) {
        if (!checkBranchExisting(ID)) return false;
        try {
            db = this.getWritableDatabase();

            String IDString = "";
            for (int i = 0; i < serviceIDS.length; i++) {
                IDString += serviceIDS[i] + ",";
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("branchID", ID);
            contentValues.put("name", getDatabaseData(ID, DATABASE_DATA.name).toString());
            contentValues.put("openingHours", getDatabaseData(ID, DATABASE_DATA.openingHours).toString());
            contentValues.put("closingHours", getDatabaseData(ID, DATABASE_DATA.closingHours).toString());
            contentValues.put("servicesProvided", IDString.substring(0, IDString.length()-1));
            contentValues.put("address", getDatabaseData(ID, DATABASE_DATA.address).toString());
            contentValues.put("phoneNumber", getDatabaseData(ID, DATABASE_DATA.phoneNumber).toString());

            db.update(BRANCH_TABLE, contentValues, "branchID=?", new String[]{""+ID});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] getServicesProvided(int ID) {
        String temp = (String) getDatabaseData(ID, DATABASE_DATA.servicesProvided);
        return temp.split(",");
    }

    public boolean updateAddress(int ID, String address) {
        if (!checkBranchExisting(ID)) return false;
        try {
            db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("branchID", ID);
            contentValues.put("name", getDatabaseData(ID, DATABASE_DATA.name).toString());
            contentValues.put("openingHours", getDatabaseData(ID, DATABASE_DATA.openingHours).toString());
            contentValues.put("closingHours", getDatabaseData(ID, DATABASE_DATA.closingHours).toString());
            contentValues.put("servicesProvided", getDatabaseData(ID, DATABASE_DATA.servicesProvided).toString());
            contentValues.put("address", address);
            contentValues.put("phoneNumber", getDatabaseData(ID, DATABASE_DATA.phoneNumber).toString());

            db.update(BRANCH_TABLE, contentValues, "branchID=?", new String[]{""+ID});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updatePhoneNumber(int ID, String phoneNumber) {
        if (!checkBranchExisting(ID)) return false;
        try {
            db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("branchID", ID);
            contentValues.put("name", getDatabaseData(ID, DATABASE_DATA.name).toString());
            contentValues.put("openingHours", getDatabaseData(ID, DATABASE_DATA.openingHours).toString());
            contentValues.put("closingHours", getDatabaseData(ID, DATABASE_DATA.closingHours).toString());
            contentValues.put("servicesProvided", getDatabaseData(ID, DATABASE_DATA.servicesProvided).toString());
            contentValues.put("address", getDatabaseData(ID, DATABASE_DATA.address).toString());
            contentValues.put("phoneNumber", phoneNumber);

            db.update(BRANCH_TABLE, contentValues, "branchID=?", new String[]{""+ID});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] getBranchAddresses() {
        db = this.getWritableDatabase();

        String[] projection = new String[]{"address"};

        Cursor cursor = db.query(BRANCH_TABLE, projection, null, null, null, null, null);

        String[] branchNames = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            branchNames[i] = cursor.getString(cursor.getColumnIndex("address"));
        }
        return branchNames;
    }
}
