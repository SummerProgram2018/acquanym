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

import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.backend.login.DBLogin;
import com.ei8htideas.acquanym.backend.backend.login.DBLoginParams;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mConfirmButton;
    private Button mNewAcctButton;

    private ProgressDialog progress;

    private String username;
    private String password;

    public void onSuccess() {
        progress.dismiss();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void onFail() {
        progress.hide();
        mEmailView.setText("");
        mPasswordView.setText("");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Incorrect username or password");
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mConfirmButton = (Button) findViewById(R.id.sign_in_btn);
        mConfirmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mNewAcctButton = (Button) findViewById(R.id.sign_in_register);
        mNewAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        progress = new ProgressDialog(this);
        progress.setTitle("Verifying credentials");
        progress.setMessage("Please wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }

    private void attemptLogin() {
        username = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        DBLoginParams params = new DBLoginParams();
        params.username = username;
        params.password = password;
        params.activity = this;

        progress.show();
        new DBLogin().execute(params);
    }
}

