package com.avisys.allinone;

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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.avisys.allinone.RoomDatabase.PersonDetailsActivity;
import com.avisys.allinone.contentprovider.MyContentProviderActivity;
import com.avisys.allinone.lifecycle.LifeCycleActivity;
import com.avisys.allinone.maps.GoogleMapsActivity;
import com.avisys.allinone.multiplesprite.MultiSpriteActivity;
import com.avisys.allinone.pagination.PaginationActivity;
import com.avisys.allinone.runtimepermission.MultipleRunningPermissionActivity;
import com.avisys.allinone.runtimepermission.RuntimePermissionActivity;
import com.avisys.allinone.service.internet.CheckInternetActivity;
import com.avisys.allinone.service.mediaplayer.MediaPlayerActivity;
import com.avisys.allinone.service.wifimanager.WifiServiceActivity;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView roomDatabase, lifeCycle, pagination, touchGameView,
            singleRuntimePermission, multiRuntimePermission, multiSprite, content_provider,
            locationService, wifi, mediaPlayer, chekNetwork;
    private Intent intent;
    private final String CAMERA = Manifest.permission.CAMERA;
    private final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private String[] PERMISSION_ARRAY = {Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private final int PERMISSION_CODE = 100;
    private SharedPreferences sharedPreferences;
    private Dialog mDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /*
         * Create the object for SharedPreferences with two params @field appDatabaseName and @field ModePrivate
         * */
        sharedPreferences = getSharedPreferences("AppDatabase", MODE_PRIVATE);
        init();
    }

    private void init() {
        intent = new Intent();
        roomDatabase = findViewById(R.id.roomDatabase);
        lifeCycle = findViewById(R.id.lifeCycle);
        pagination = findViewById(R.id.pagination);
        touchGameView = findViewById(R.id.touchGameView);
        singleRuntimePermission = findViewById(R.id.singleRunTimePermission);
        multiRuntimePermission = findViewById(R.id.multiRuntimePermission);
        multiSprite = findViewById(R.id.multiSprite);
        content_provider = findViewById(R.id.content_resolver);
        locationService = findViewById(R.id.location_service);
        wifi = findViewById(R.id.wifi);
        mediaPlayer = findViewById(R.id.media_player);
        chekNetwork = findViewById(R.id.network);
        /* setOnClickListener*/
        roomDatabase.setOnClickListener(this);
        lifeCycle.setOnClickListener(this);
        pagination.setOnClickListener(this);
        touchGameView.setOnClickListener(this);
        singleRuntimePermission.setOnClickListener(this);
        multiRuntimePermission.setOnClickListener(this);
        multiSprite.setOnClickListener(this);
        content_provider.setOnClickListener(this);
        locationService.setOnClickListener(this);
        wifi.setOnClickListener(this);
        mediaPlayer.setOnClickListener(this);
        chekNetwork.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.roomDatabase:
                startActivity(new Intent(view.getContext(), PersonDetailsActivity.class));
                break;
            case R.id.lifeCycle:
                startActivity(new Intent(view.getContext(), LifeCycleActivity.class));
                break;
            case R.id.pagination:
                startActivity(new Intent(view.getContext(), PaginationActivity.class));
                break;
            case R.id.touchGameView:
                startActivity(new Intent(view.getContext(), MainActivity.class));
                break;
            case R.id.singleRunTimePermission:
                startActivity(new Intent(view.getContext(), RuntimePermissionActivity.class));
                break;
            case R.id.multiRuntimePermission:
                startActivity(new Intent(view.getContext(), MultipleRunningPermissionActivity.class));
                break;
            case R.id.multiSprite:
                startActivity(new Intent(view.getContext(), MultiSpriteActivity.class));
                break;
            case R.id.content_resolver:
                startActivity(new Intent(view.getContext(), MyContentProviderActivity.class));
                break;
            case R.id.location_service:
                checkMultiRuntimePermission();
                break;
            case R.id.ok:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(PERMISSION_ARRAY, PERMISSION_CODE);
                mDialog.dismiss();
                break;
            case R.id.cancel:
                mDialog.dismiss();
                break;
            case R.id.wifi:
                startActivity(new Intent(this, WifiServiceActivity.class));
                break;
            case R.id.media_player:
                startActivity(new Intent(this, MediaPlayerActivity.class));
                break;
            case R.id.network:
                startActivity(new Intent(this, CheckInternetActivity.class));
                break;


        }
    }

    private void checkMultiRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            /*
             * This code of line will be executed only when if all the permission has already been accepted.
             * */
            //Do your task
            startActivity(new Intent(this, GoogleMapsActivity.class));
            Toast.makeText(this, "All permission accepted", Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (isUserDeniedPermissionAtFirst()) {
                    /*
                     * If user has already been denied permission but don't deny for never ask permission then
                     * this code of line will get executed.
                     * */
                    showDialogUnderShouldShowRequestPermissionRationale();
                } else {
                    if (sharedPreferences.getBoolean("isUserDeniedPermissionAtFirst", false)) {
                        /*
                         * This code of line will get executed only when user has already denied the permission
                         * and also he has clicked on the never ask permission as well.
                         * */
                        showDialogWhenUserAlreadyClickedOnNeverAskPermission();
                    } else {
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
            } else {
                /*
                 * @Below marsh mellow all the permission automatically gets accepted.
                 * so no need to write code for this.
                 */

            }
        }
    }// Method closed

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isUserDeniedPermissionAtFirst() {
        int i = 0;
        while (i < PERMISSION_ARRAY.length) {
            if (ContextCompat.checkSelfPermission(this, PERMISSION_ARRAY[i]) == PackageManager.PERMISSION_GRANTED) {
                return false;/* if permission has already been accepted then no need to ask permission again */
            } else {
                return shouldShowRequestPermissionRationale(PERMISSION_ARRAY[i]);/* As soon as it returns true
                 @method getDenied called */
            }
        }
        return false;/* By default it will be false */

    }

    private void showDialogWhenUserAlreadyClickedOnNeverAskPermission() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Needed permission");
        alertDialog.setMessage("To use google map functionality you will have to accept the permission");
        /*set positive button*/
        alertDialog.setPositiveButton("open settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
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

    private void showDialogUnderShouldShowRequestPermissionRationale() {
        if (mDialog == null) {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("OnRequestPer", "All permission accepted");
                    startActivity(new Intent(this, GoogleMapsActivity.class));
                } else {
                    // Don't do anything
                }
                break;
            default:
                Toast.makeText(this, "Default not accepted", Toast.LENGTH_SHORT).show();
        }
    }
}