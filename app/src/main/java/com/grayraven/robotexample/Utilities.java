package com.grayraven.robotexample;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jim on 7/3/2016.
 */
public class Utilities {
    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
