package com.example.daras.robocare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

    Button Login;
    Button Register;
    EditText EmailID;
    EditText Password;
    String TempEmail;
    String TempPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnRegister);
        EmailID = (EditText) findViewById(R.id.txtEmail);
        Password = (EditText) findViewById(R.id.txtPassword);


    }

    public void LoginClick(View v)
    {
        try
        {
            if(ValidateUser())
            {
                hideKeyboard();
                Toast.makeText(Login_Activity.this,"ROBO CARE Welcomes You",Toast.LENGTH_LONG).show();
                Intent Options = new Intent(Login_Activity.this, com.example.daras.robocare.Options.class);
                startActivity(Options);

            }
            else
            {
                Toast.makeText(Login_Activity.this,"Login Failed",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex)
        {
         ex.printStackTrace();
        }

    }



    public boolean ValidateUser() {

        boolean isValid = true;
        try
        {
            TempEmail=EmailID.getText().toString();
            TempPassword=Password.getText().toString();
            if(!TempEmail.equals("dara.sandeep91@gmail.com"))
            {
                EmailID.setError("Enter a valid EmailID");
                EmailID.requestFocus();
                isValid = false;
            }
            else if(!TempPassword.equals("password")){
                Password.setError("Enter a valid password");
                Password.requestFocus();
                isValid = false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return isValid;
    }

    public void RegisterOnclick(View view)
    {
        try
        {
            Intent Options = new Intent(Login_Activity.this,Register.class);
            startActivity(Options);

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



    public void hideKeyboard(){

        try
        {
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }


}
