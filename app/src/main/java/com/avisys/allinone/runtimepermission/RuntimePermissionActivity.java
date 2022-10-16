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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avisys.allinone.R;

import java.util.ArrayList;
import java.util.List;

public class RuntimePermissionActivity extends AppCompatActivity implements View.OnClickListener {

    Dialog mDialog;
    private int permission_index = 0;
    private SharedPreferences sharedPreferences;
    private String TAG = "RuntimePermissionActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime_permission);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSingleCameraPermission();
    }

    public void getMobileScreenHeightAndWidth(){
        // To get each mobile device height and width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.e("Screen Height:"+displayMetrics.heightPixels,"screen Width : "+displayMetrics.widthPixels);
    }

    public void checkSingleCameraPermission(){
        // Init sharedPref
        sharedPreferences = getSharedPreferences("App_Database",MODE_PRIVATE);
        Log.e("Take Camera per","in");
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            iAmReadyToTakePicture();
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("Take Camera per","3");
            /**
             * Through @method shouldShowRequestPermissionRationale(): we can show custom pop up
             * and by setting positive or negative button user can re-set the permission again
             * if user click on positive button(i.e to access permission) we can call @method requestPermissions(PERMISSIONS,CODE)
             * If user click on negative button(i.e to avoid permission) we can call disable the custom pop up.
             * */
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                showUserUnderstandablePopUp();
                Log.d(TAG, "checkSingleCameraPermission: 1");
            }else {
                if(sharedPreferences.getBoolean("USER_ASKED_STORAGE_PERMISSION_BEFORE",false)){
                    //If User was asked permission before and denied
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                    alertDialogBuilder.setTitle("Permission needed");
                    alertDialogBuilder.setMessage("Storage permission needed for accessing photos");
                    alertDialogBuilder.setPositiveButton("Open Setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(),
                                    null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: Cancelling");
                        }
                    });
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();
                    Log.d(TAG, "checkSingleCameraPermission: 2");
                }
                else {
                    Log.e("Take Camera per", "4");
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    Toast.makeText(this, "Needs camera permission", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("USER_ASKED_STORAGE_PERMISSION_BEFORE", true);
                    editor.apply();
                    Log.d(TAG, "checkSingleCameraPermission: 3");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Take camera per","under permission");
        switch (requestCode){
            case 100:
                if(grantResults.length>0&&grantResults[permission_index]==PackageManager.PERMISSION_GRANTED){
                    iAmReadyToTakePicture();
                    Log.d(TAG, "onRequestPermissionsResult: 4");
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                            showUserUnderstandablePopUp();
                            Log.d(TAG, "onRequestPermissionsResult: 5");
                        }
                    Log.d(TAG, "onRequestPermissionsResult: 6");
                }
                break;
        }

    }

    private void iAmReadyToTakePicture(){
        Toast.makeText(this, "All permission granted", Toast.LENGTH_SHORT).show();
    }

    private void showUserUnderstandablePopUp(){
        Toast.makeText(this, "Set custom popup", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showUserUnderstandablePopUp: "+mDialog);
        if (mDialog==null) {
            mDialog = new Dialog(this);
            mDialog.setContentView(R.layout.camer_permission_popup_after_denied);
            mDialog.setCancelable(false);
            TextView ok = mDialog.findViewById(R.id.ok);
            TextView cancel = mDialog.findViewById(R.id.cancel);
            ok.setOnClickListener(this);
            cancel.setOnClickListener(this);
            mDialog.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok:
                mDialog.dismiss();
                requestPermissions(new String[]{Manifest.permission.CAMERA},100);
                break;
            case R.id.cancel:
                mDialog.dismiss();
                break;
        }
    }
}