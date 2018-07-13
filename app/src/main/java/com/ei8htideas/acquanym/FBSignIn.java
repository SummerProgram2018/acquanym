package com.ei8htideas.acquanym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.background.Subprocess;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Archit on 9/07/2018.
 */

public class FBSignIn extends AppCompatActivity {
    TextView txtStatus;
    LoginButton login_button;
    CallbackManager callbackManager;
    private Details userDetail=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.sign_in);
        initializeControls();
        loginWithFB();


    }
    private void initializeControls(){
        callbackManager = CallbackManager.Factory.create();
        txtStatus = (TextView)findViewById((R.id.txtStatus));
        login_button=(LoginButton)findViewById((R.id.Login_button));



        login_button.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
    }

    private void loginWithFB() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String email;
                txtStatus.setText("Login Success");
                startActivity(new Intent(sign_in.this, MainActivity.class));
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("Response",response.toString());
                                //name,age,gender,occupation
                                String userName;
                                //String gender;
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try{
                                    userName = object.getString("name");
                                    String email = response.getJSONObject().getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                    String [] array = birthday.split("/", 3);
                                    int age = 2018 - Integer.parseInt(array[2]);
                                    getUserInfo(userName,age);
                                    txtStatus.setText(" "+ userName + " " + age);
                                }catch (Exception e){

                                }


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");

                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                txtStatus.setText("Login Cancelled");

            }

            @Override
            public void onError(FacebookException e) {
                txtStatus.setText("Login Error"+ e.getMessage());
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void getUserInfo(String name, int age){
        userDetail.name = name;

       // Intent myIntent = new Intent(this, MainActivity.class);
       // this.startActivity(myIntent);
        Intent intent = new Intent(getBaseContext(), Subprocess.class);
        startService(intent);
    }

}
