package com.b1strudio.srvdabba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.b1strudio.srvdabba.Common.Common;
import com.b1strudio.srvdabba.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;



public class SignIn extends AppCompatActivity {


    EditText edtPhone,edtPassword;
    Button btnSignIn;

    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(edtPhone.getText().toString(),edtPassword.getText().toString());
            }
        });

    }

    private void signInUser(String phone, String password) {
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please wait .........");
        mDialog.show();

        final String localPhone = phone;
        final String localPassword = password;
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(localPhone).exists()) {
                    mDialog.dismiss();

                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
                    if (Boolean.parseBoolean(user.getIsStaff())) {

                        if (user.getPassword().equals(localPassword)) {
                            //Login successful}
 //                           Toast.makeText(SignIn.this, "Login success", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(SignIn.this,Home.class);
                            Common.currentUser = user;
                            startActivity(login);
                            finish();
                        } else
                            Toast.makeText(SignIn.this, "Wrong Userid or Password", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(SignIn.this, "Please Login with staff account", Toast.LENGTH_LONG).show();

                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this,"User not exists",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
