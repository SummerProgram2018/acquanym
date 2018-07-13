package com.ei8htideas.acquanym;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ei8htideas.acquanym.backend.DBWriter;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;
import com.ei8htideas.acquanym.backend.backend.login.DBLoginParams;
import com.ei8htideas.acquanym.backend.backend.login.DBRegister;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mNameView;
    private AutoCompleteTextView mDobView;
    private AutoCompleteTextView mTitleView;
    private AutoCompleteTextView mDescView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mNameView = (AutoCompleteTextView) findViewById(R.id.name);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordConfirmView = (EditText) findViewById(R.id.cpassword);

        mDobView = (AutoCompleteTextView) findViewById(R.id.dob);
        mTitleView = (AutoCompleteTextView) findViewById(R.id.title);
        mDescView = (AutoCompleteTextView) findViewById(R.id.desc);

        Button mEmailSignInButton = (Button) findViewById(R.id.register_btn);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Verifying credentials");
        progress.setMessage("Please wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }

    private double latitude;
    private double longitude;
    private boolean success;
    private String username;
    private String name;
    private String password;
    private String dob;
    private String title;
    private String desc;

    private void attemptLogin() {
        username = mEmailView.getText().toString();
        name = mNameView.getText().toString();
        password = mPasswordView.getText().toString();
        dob = mDobView.getText().toString();
        title = mTitleView.getText().toString();
        desc = mDescView.getText().toString();
        String cPassword = mPasswordConfirmView.getText().toString();
        if (!password.equals(cPassword)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Passwords do not match");
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();
            mPasswordView.setText("");
            mPasswordConfirmView.setText("");
            return;
        }

        getMyLoc();
    }

    private void loginFail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cannot retrieve location");
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
        mPasswordView.setText("");
        mPasswordConfirmView.setText("");
    }

    private void continueLogin() {
        DBLoginParams params = new DBLoginParams();
        params.username = username;
        params.password = password;
        params.dob = dob;
        params.title = title;
        params.desc = desc;
        params.latitude = latitude;
        params.longitude = longitude;
        params.name = name;
        params.register = this;

        Log.i("Success", "success1");
        progress.show();
        new DBRegister().execute(params);
    }

    public void onSuccess() {
        progress.dismiss();
        Log.i("Success", "success2");
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }

    private void getMyLoc() {
        success = false;
            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("Location", "No perms");
                String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, perms, 1);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Fail", "fail1");
                    loginFail();
                    return;
                }
            }
            client.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            continueLogin();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("Fail", "fail2");
                            loginFail();
                        }
                    });
    }

    public void onFail() {
        Log.i("Fail", "fail3");
        progress.hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Username already exists");
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();

        mEmailView.setText("");
        mPasswordView.setText("");
        mPasswordConfirmView.setText("");
    }
}

