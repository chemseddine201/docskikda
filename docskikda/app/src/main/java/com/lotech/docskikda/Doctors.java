package com.lotech.docskikda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Doctors extends AppCompatActivity {

    private static DatabaseAccess database;
    private ArrayList doctors;
    Activity act;
    private DoctorAdapter doctorAdapter;
    String speciality_id = "";
    RecyclerView recyclerView;
    final int perpage = 10;
    Activity mActivity;
    public String getSpeciality_id() {
        return speciality_id;
    }

    public void setSpeciality_id(String speciality_id) {
        this.speciality_id = speciality_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        int total_dotors;
        super.onCreate(savedInstanceState);
        act = this;
        database = DatabaseAccess.getInstance(this);
        setContentView(R.layout.activity_doctors);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("speciality_id");
        speciality_id = b.getString("id");
        if("".equals(speciality_id)){
            Toast.makeText(getApplicationContext(), "Error empty id passed." , Toast.LENGTH_SHORT).show();
            return;
        }

        setSpeciality_id(speciality_id);
        databaseAccess.open();
        doctors = databaseAccess.getDoctorsWithLimit(getSpeciality_id() , "0", Integer.toString(perpage));
        total_dotors = databaseAccess.getCountDotors(getSpeciality_id());
        //databaseAccess.close();

        if (doctors.size() < 1) {
            Toast.makeText(getApplicationContext(), "No Doctors To Show." , Toast.LENGTH_SHORT).show();
            return;
        }
        //find view by id and attaching adapter for the RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorAdapter = new DoctorAdapter(recyclerView, doctors, this);
        recyclerView.setAdapter(doctorAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Doctor doc = (Doctor) doctors.get(position);
                int id = doc.getId();
            Intent doctorsActivity = new Intent(getApplicationContext(), Profile.class);
                Bundle b = new Bundle();
                b.putString("id" , String.valueOf(id));
                doctorsActivity.putExtra("doctor_id", b);
                startActivity(doctorsActivity);
            }

            @Override
            public void onLongClick(View view, int position) {
                return;
            }
        }));

        //set load more listener for the RecyclerView adapter
        final int finalTotal_dotors = total_dotors;

        doctorAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (doctors.size() <= finalTotal_dotors) {
                    doctors.add(null);
                    doctorAdapter.notifyItemInserted((int) (doctors.size() - 1));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<Doctor> innerDoctors;
                            doctors.remove(doctors.size() - 1);
                            doctorAdapter.notifyItemRemoved(doctors.size());
                            int index = doctors.size();
                            int end = index + perpage;
                            innerDoctors = databaseAccess.getDoctorsWithLimit(getSpeciality_id(), Integer.toString(index) , Integer.toString(end) );
                            if(innerDoctors != null){
                                doctors.addAll(innerDoctors);
                                doctorAdapter.notifyDataSetChanged();
                                doctorAdapter.setLoaded();
                            } else {
                                Toast.makeText(getBaseContext() , "All Doctors Loaded", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 500);
                } else {
                    Toast.makeText(getBaseContext(), "All doctors loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() < 3) return false;
                ArrayList<Doctor> list = database.search(newText);
                doctorAdapter = new DoctorAdapter(recyclerView, list, act);
                recyclerView.setAdapter(doctorAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
    }