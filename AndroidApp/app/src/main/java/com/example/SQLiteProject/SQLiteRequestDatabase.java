package com.example.SQLiteProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.javaClasses.Request;
import com.example.javaClasses.RequestMaster;

import java.io.ByteArrayOutputStream;

public class SQLiteRequestDatabase extends SQLiteDatabaseMaster {
    public SQLiteRequestDatabase(Context context) {
        super(context);
    }

    public enum DATABASE_DATA {
        requestID,
        firstName,
        lastName,
        address,
        dateOfBirth,
        proofOfResidence,
        proofOfStatus,
        photoOfUser,
        otherData
    }

    ;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean createNewRequest(Request request) {
        db = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("requestID", request.master.getID());
            contentValues.put("firstName", request.getFirstName());
            contentValues.put("lastName", request.getLastName());
            contentValues.put("address", request.getAddress());
            contentValues.put("dateOfBirth", request.getDateOfBirth());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            request.getProofOfResidence().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageToAdd = stream.toByteArray();
            contentValues.put("proofOfResidence", imageToAdd);
            stream.reset();

            request.getProofOfStatus().compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageToAdd = stream.toByteArray();
            contentValues.put("proofOfStatus", imageToAdd);
            stream.reset();

            request.getPhotoOfUser().compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageToAdd = stream.toByteArray();
            contentValues.put("photoOfUser", imageToAdd);
            stream.close();

            contentValues.put("otherData", request.getOtherInformationRequired());

            long result = db.insert(REQUEST_TABLE, null, contentValues);
            if (result == -1) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Request[] getRequests(int ID, RequestMaster template) {
        if (!checkExistingRequest(ID)) return null;

        db = this.getWritableDatabase();

        String[] projection = new String[]{"requestID", "firstName", "lastName", "address", "dateOfBirth", "proofOfResidence", "proofOfStatus", "photoOfUser", "otherData"};

        String selection = "requestID=?";
        String[] selectionArgs = {""+ID};


        Cursor cursor = db.query(REQUEST_TABLE, projection, selection, selectionArgs, null, null, null);
        Request[] requests = new Request[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String firstName = cursor.getString(cursor.getColumnIndex(projection[1]));
            String lastName = cursor.getString(cursor.getColumnIndex(projection[2]));
            String address = cursor.getString(cursor.getColumnIndex(projection[3]));
            String dateOfBirth = cursor.getString(cursor.getColumnIndex(projection[4]));
            Request newRequest = new Request(template, firstName, lastName, dateOfBirth, address);

            byte[] POR = cursor.getBlob(cursor.getColumnIndex(projection[5]));
            byte[] POS = cursor.getBlob(cursor.getColumnIndex(projection[6]));
            byte[] POU = cursor.getBlob(cursor.getColumnIndex(projection[7]));

            newRequest.setProofOfResidence(BitmapFactory.decodeByteArray(POR, 0, POR.length));
            newRequest.setProofOfStatus(BitmapFactory.decodeByteArray(POS, 0, POR.length));
            newRequest.setPhotoOfUser(BitmapFactory.decodeByteArray(POU, 0, POR.length));

            requests[i] = newRequest;
        }
        return requests;
    }

    public boolean checkExistingRequest(int ID) {
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from requestData where requestID = ? ", new String[]{"" + ID});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

}

