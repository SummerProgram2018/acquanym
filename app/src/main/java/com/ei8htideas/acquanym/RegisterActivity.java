package com.ei8htideas.acquanym;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ei8htideas.acquanym.backend.backend.login.DBLoginParams;
import com.ei8htideas.acquanym.backend.backend.login.DBRegister;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mNameView;
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

    private void attemptLogin() {
        String username = mEmailView.getText().toString();
        String name = mNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String cPassword = mPasswordConfirmView.getText().toString();
        if(!password.equals(cPassword)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Passwords do not match");
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();
            mPasswordView.setText("");
            mPasswordConfirmView.setText("");
            return;
        }

        DBLoginParams params = new DBLoginParams();
        params.username = username;
        params.password = password;
        params.name = name;
        params.register = this;

        progress.show();
        new DBRegister().execute(params);
    }

    public void onSuccess() {
        progress.dismiss();
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));

    }

    public void onFail() {
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

