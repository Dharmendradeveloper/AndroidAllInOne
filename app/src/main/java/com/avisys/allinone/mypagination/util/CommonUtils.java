package com.avisys.allinone.mypagination.util;

import android.content.Context;
import android.widget.Toast;

public class CommonUtils {
    public static final int PAGE_SIZE = 6;
    public static long FIRST_PAGE = 1;

    public static void showToast(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
