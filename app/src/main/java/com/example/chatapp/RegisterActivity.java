package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText username, email, password;
    Button btn_register;

    FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("server/saving-data/fireblog") ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_email) ){
                    Toast.makeText(RegisterActivity.this, "Fill all information", Toast.LENGTH_SHORT).show();
                }else if (txt_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters" , Toast.LENGTH_SHORT).show();
                }else {
                    register(txt_username, txt_email, txt_password);
                }
            }
        });
    }

//    private void register (String username, String email, String password){
////        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////            @Override
////            public void onComplete(@NonNull Task<AuthResult> task) {
////
////                if (task.isSuccessful()){
////
////                    FirebaseUser user = mAuth.getCurrentUser();
////
////                    reference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
////
////                    if ( user != null ){
////
////                        HashMap<String, Object> hashMap = new HashMap<>();
////                        hashMap.put("username", username);
////                        hashMap.put("email", email);
////                        hashMap.put("id", user.getUid());
////
////
////
////                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
////                            @Override
////                            public void onComplete(@NonNull Task<Void> task) {
////                                startActivity(new Intent (RegisterActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
////                            }
////                        });
////                    }
////                }
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Toast.makeText(RegisterActivity.this, "Email or Password is not valid", Toast.LENGTH_SHORT).show();
////            }
////        });
//        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                FirebaseUser user = mAuth.getCurrentUser();
//
//                reference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
//
//                if ( user != null ) {
////
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("username", username);
//                        hashMap.put("email", email);
//                        hashMap.put("id", user.getUid());
//
//                        reference.setValue(hashMap);
//                }
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    private void register(final String username, String password, final String email) {

    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful()) {

                FirebaseUser user = mAuth.getCurrentUser();

                reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());


                if (user!=null) {

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("username", username);
                    hashMap.put("email", email);
                    hashMap.put("id", user.getUid());
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()) {

                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(RegisterActivity.this,
                                        StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK ));


                            }
                        }
                    });

                }


            }



        }
    });

}
}