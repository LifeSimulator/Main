package subversion_team.cs.brandeis.edu.lifesimulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zhengyangzhou on 11/15/17.
 */

public class SignUp extends Activity{
    DatabaseHelper helper = new DatabaseHelper(this);

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        reg();
        returnPage();
    }

    public void reg(){
        Button reg = (Button) findViewById(R.id.Bsignupbutton);
        View.OnClickListener saveBack = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.email);
                EditText pass1 = (EditText) findViewById(R.id.password);
                EditText pass2 = (EditText) findViewById(R.id.repassword);

                String emailstr = email.getText().toString();
                String pass1str = pass1.getText().toString();
                String pass2str = pass2.getText().toString();


                if (!pass1str.equals(pass2str)) {
                    Toast pass = Toast.makeText(SignUp.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                    pass.show();
                }else if(pass1str.length() < 6){
                    Toast infoPass = Toast.makeText(SignUp.this, "The length passwords is too short. Please make your password have at least 6 characters", Toast.LENGTH_SHORT);
                    infoPass.show();
                }else if(!emailstr.contains("@")){
                    Toast infoPass = Toast.makeText(SignUp.this, "Not a valid email address. Please enter another one", Toast.LENGTH_SHORT);
                    infoPass.show();
                }else{
                    if(helper.isEmailExisted(emailstr)){
                        Toast emailError = Toast.makeText(SignUp.this, "Email has been registered! Try another one!", Toast.LENGTH_SHORT);
                        emailError.show();
                    }else {
                        Contact c = new Contact(emailstr, pass1str);
                        helper.insertContact(c);
                        Toast suss = Toast.makeText(SignUp.this, "Successfully registered!", Toast.LENGTH_SHORT);
                        suss.show();

                        // Gives the user the n00b achievement for signing up and creating an account
                        helper.addAchievement(DatabaseHelper.a1Name, emailstr);
                        Log.d("SIGNUP", "User " + emailstr + " earned " + DatabaseHelper.a1Name);
                    }
                }
            }
        };
        reg.setOnClickListener(saveBack);
    }


    public void returnPage() {
        //Return page
        Button returnB = (Button) findViewById(R.id.returnbutton);
        View.OnClickListener cleanBack = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(RESULT_CANCELED,data);
                finish();

            }
        };
        returnB.setOnClickListener(cleanBack);
    }


}
