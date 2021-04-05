/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.lotech.docskikda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


  //http://www.devexchanges.info/2017/02/android-recyclerview-dynamically-load.html
  ArrayList<Speciality>  AllSpecialities;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
    databaseAccess.open();
    AllSpecialities = databaseAccess.getSpecialities();
    databaseAccess.close();

    if(AllSpecialities != null){
      Log.d("APP", "There is some specialities");
    }else{
      Log.d("APP", "No Specialities");
    }

    GridView gridView = (GridView)findViewById(R.id.gridview);
    final SpecialitiesAdapter specialitiesAdapter = new SpecialitiesAdapter(this, AllSpecialities);
    gridView.setAdapter(specialitiesAdapter);
    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Speciality speciality = AllSpecialities.get(position);
          Log.d("APP", "speciality_id: " + speciality.getId());
        try {

          Intent doctorsActivity = new Intent(MainActivity.this, Doctors.class);
          Bundle b = new Bundle();
          b.putString("id" , speciality.getId());
          doctorsActivity.putExtra("speciality_id", b);
          startActivity(doctorsActivity);

        } catch(Exception e) {
          e.printStackTrace();
        }
        specialitiesAdapter.notifyDataSetChanged();
      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }



}
