package com.avisys.allinone.mypagination.view.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class BindingImage {
     @BindingAdapter("profileImage")
    public static void setImageResource(ImageView imageView, String imageUrl){
         Glide.with(imageView.getContext()).load(imageUrl).
                 apply(new RequestOptions().circleCrop()).into(imageView);
    }
}
