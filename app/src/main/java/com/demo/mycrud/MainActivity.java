
package com.demo.mycrud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    EditText nameEt;
    Button addBtn, readBtn;
    TextView readTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nameEt = findViewById(R.id.name);
        addBtn = findViewById(R.id.add_btn);

        readBtn = findViewById(R.id.read_btn);
        readTv = findViewById(R.id.read_tv);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (nameEt.getText().toString().trim().length() > 0) {

                    String key = databaseReference.push().getKey();

                    databaseReference.child(key).child("name").setValue(nameEt.getText().toString().trim());
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();


                }
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference.child("u2").child("name").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        readTv.setText(snapshot.getValue(String.class));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}