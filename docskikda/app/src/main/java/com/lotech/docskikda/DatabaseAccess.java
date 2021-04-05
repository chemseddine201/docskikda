package com.lotech.docskikda;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all specialities from the database.
     *
     * @return a List of specialities
     */

    public ArrayList<Speciality>  getSpecialities() {
        ArrayList<Speciality> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Specialities ORDER BY id ASC", null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(1);
                String id = cursor.getString(0);
                String imageString = cursor.getString(2);
                String[] arrImage = imageString.split(".png");
                String imageName  = arrImage[0];
                list.add(new Speciality(name, id, imageName));
                cursor.moveToNext();
            }
            cursor.close();
            return list;
        }
        cursor.close();
        return null;
    }

    /**
     * Read all doctors from the database.
     *
     * @return a List of doctors
     */

    public ArrayList getDoctors(String speciality_id) {
        ArrayList<Doctor> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM doctors WHERE speciality_id =  ?", new String[] {speciality_id});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int doctor_id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String speciality = cursor.getString(3);
                String local = cursor.getString(4);
                list.add(new Doctor(doctor_id, name, phone, local, speciality));
                cursor.moveToNext();
            }
            cursor.close();
            return list;
        }
        cursor.close();
        return null;
    }

    /**
     * Read doctor profile from the database.
     *
     * @return a a single doctor profile
     */

    public ProfileHandler getDoctor(String id) {
        Cursor cursor = database.rawQuery("SELECT * FROM doctors WHERE id = ? LIMIT 1" , new String[] {id} );
        if (cursor.moveToFirst()) {
            int doctor_id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            //String speciality = cursor.getString(3);
            String local = cursor.getString(5);
            cursor.moveToNext();
            cursor.close();
            ProfileHandler ph = new ProfileHandler(doctor_id, name, phone, local);
            cursor.close();
            return ph;
        }
        cursor.close();
        return null;
    }




    public ArrayList getDoctorsWithLimit(String id, String minId , String maxId) {

        ArrayList<Doctor> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM doctors WHERE speciality_id =  ? LIMIT ?, ?" , new String[] {id, minId, maxId} );
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int doctor_id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String speciality = cursor.getString(3);
                String local = cursor.getString(5);
                Log.d("APP", name  + " / "  + phone + "/ " + local);
                list.add(new Doctor(doctor_id, name, phone, local, speciality));
                cursor.moveToNext();
            }
            cursor.close();
            return list;
        }

        cursor.close();
        return null;
    }

    public int getCountDotors(String id){
        Cursor cursor = database.rawQuery("SELECT count(*) as total FROM doctors WHERE speciality_id = ?" , new String[] {id} );
        if (cursor.moveToFirst()){
            String total = cursor.getString(0);
            cursor.close();
            return Integer.parseInt(total);
        }
        cursor.close();
        return 0;
    }

    public ArrayList<Doctor> search (String searchtxt) {
        ArrayList list = new ArrayList<Doctor>();
        try {
            String sql = "select * from doctors where name like '%" + searchtxt + "%'";
            Cursor cursor = database.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        int doctor_id = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String phone = cursor.getString(2);
                        String speciality = cursor.getString(3);
                        String local = cursor.getString(4);
                        list.add(new Doctor(doctor_id, name, phone, local, speciality));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
        }catch (Exception e){};
        return list;
    }
}