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

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpecialitiesAdapter extends BaseAdapter {

  private final Context mContext;
  private final ArrayList<Speciality> specialities;

  public SpecialitiesAdapter(Context context, ArrayList<Speciality> specialities) {
    this.mContext = context;
    this.specialities = specialities;
  }

  @Override
  public int getCount() {
    return specialities.size();
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final Speciality speciality = specialities.get(position);

    if (convertView == null) {

      final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
      convertView = layoutInflater.inflate(R.layout.speciality_layout, null);
      final ImageView imageViewCoverArt = (ImageView)convertView.findViewById(R.id.imageview);
      final TextView nameTextView = (TextView)convertView.findViewById(R.id.textview);

      final ViewHolder viewHolder = new ViewHolder(nameTextView, imageViewCoverArt);
      convertView.setTag(viewHolder);
    }

    final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
    //prepare id by image name
    int image_id = mContext.getResources().getIdentifier(speciality.getImageResource(), "drawable", mContext.getPackageName());
    if (image_id == 0) image_id = R.drawable.giniraliste;
    //
    viewHolder.nameTextView.setText(speciality.getName());
    viewHolder.imageViewCoverArt.setImageResource(image_id);
    return convertView;
  }

  private class ViewHolder {

    private final TextView nameTextView;
    private final ImageView imageViewCoverArt;

    public ViewHolder(TextView nameTextView, ImageView imageViewCoverArt) {
      this.nameTextView = nameTextView;
      this.imageViewCoverArt = imageViewCoverArt;
    }
  }

}
