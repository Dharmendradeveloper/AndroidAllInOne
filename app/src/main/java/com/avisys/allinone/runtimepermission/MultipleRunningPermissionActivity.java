package com.avisys.allinone.runtimepermission;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avisys.allinone.R;

public class MultipleRunningPermissionActivity extends AppCompatActivity implements View.OnClickListener {
    private final int PERMISSION_CODE = 100;
    private String[] PERMISSION_ARRAY = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE};
    private final String CAMERA = Manifest.permission.CAMERA;
    private final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private SharedPreferences sharedPreferences;
    private Dialog mDialog=null;
    private final String TAG = "MultipleRunning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_running_permission);
        /*
        * Create the object for SharedPreferences with two params @field appDatabaseName and @field ModePrivate
        * */
        sharedPreferences = getSharedPreferences("AppDatabase",MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMultiRuntimePermission();
    }

    private void checkMultiRuntimePermission(){
        if (ContextCompat.checkSelfPermission(this,CAMERA)== PackageManager.PERMISSION_GRANTED&&
        ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED&&
        ContextCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            /*
            * This code of line will be executed only when if all the permission has already been accepted.
            * */
            //Do your task
            Toast.makeText(this, "All permission accepted", Toast.LENGTH_SHORT).show();
        }else {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(isUserDeniedPermissionAtFirst()){
                    /*
                    * If user has already been denied permission but don't deny for never ask permission then
                    * this code of line will get executed.
                    * */
                    showDialogUnderShouldShowRequestPermissionRationale();
                }else {
                    if(sharedPreferences.getBoolean("isUserDeniedPermissionAtFirst",false)){
                        /*
                        * This code of line will get executed only when user has already denied the permission
                        * and also he has clicked on the never ask permission as well.
                        * */
                        showDialogWhenUserAlreadyClickedOnNeverAskPermission();
                    }else {
                        /*
                         *The control comes here first time if user has already not seen the Dialog of permission.
                         *  */
                        requestPermissions(PERMISSION_ARRAY, PERMISSION_CODE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        /*
                         * If dialog appear at first we can make the shared pref to store value as true
                         * */
                        editor.putBoolean("isUserDeniedPermissionAtFirst", true);
                        editor.apply();
                    }
                }
            }else {
                /*
                * @Below marsh mellow all the permission automatically gets accepted.
                * so no need to write code for this.
                * */
            }
        }
    }// Method closed

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isUserDeniedPermissionAtFirst(){
        int i=0;
        while (i<PERMISSION_ARRAY.length) {
            if (ContextCompat.checkSelfPermission(this, PERMISSION_ARRAY[i]) == PackageManager.PERMISSION_GRANTED) {
                return false;/* if permission has already been accepted then no need to ask permission again */
            }else {
                return shouldShowRequestPermissionRationale(PERMISSION_ARRAY[i]);/* As soon as it returns true
                 @method getDenied called */
            }
        }
       return false;/* By default it will be false */

    }

    private void showDialogWhenUserAlreadyClickedOnNeverAskPermission(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Needed permission");
        alertDialog.setMessage("To use the corresponding functionality you will have to accept the permission");
        /*set positive button*/
        alertDialog.setPositiveButton("open settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        /* set negative button */
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        /* create alert dialog using alertDialog.create() where alertDialog is the reference of AlertDialog.Builder*/
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void showDialogUnderShouldShowRequestPermissionRationale(){
        if (mDialog==null){
            mDialog = new Dialog(this);
            mDialog.setContentView(R.layout.camer_permission_popup_after_denied);
            mDialog.setCancelable(false);
            TextView ok = mDialog.findViewById(R.id.ok);
            TextView cancel = mDialog.findViewById(R.id.cancel);
            ok.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }
        mDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(PERMISSION_ARRAY,PERMISSION_CODE);
                mDialog.dismiss();
                break;
            case R.id.cancel:
                mDialog.dismiss();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 100:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED&&
                grantResults[1]==PackageManager.PERMISSION_GRANTED&&
                grantResults[2]==PackageManager.PERMISSION_GRANTED){
                    Log.e("OnRequestPer","All permission accepted");
                }else {
                    // Don't do anything
                }
                break;
            default:
                Toast.makeText(this, "Default not accepted", Toast.LENGTH_SHORT).show();
        }
    }
}