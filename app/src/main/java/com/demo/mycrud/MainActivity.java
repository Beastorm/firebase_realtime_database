
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    EditText nameEt;
    Button addBtn, readBtn;
    TextView readTv;
    List<String> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nameEt = findViewById(R.id.name);
        addBtn = findViewById(R.id.add_btn);

        readBtn = findViewById(R.id.read_btn);
        readTv = findViewById(R.id.read_tv);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        //   delete operation

        //1.
        databaseReference.child("appName").setValue(null);

        //2.
        databaseReference.child("appName").removeValue();

        //3. with remove listener
        databaseReference.child("appName").removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //update operation\
        Map<String, Object> updateValue = new HashMap<>();
        updateValue.put("appName","XYZ");

        databaseReference.updateChildren(updateValue);





        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {




//                snapshot.g

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });


        databaseReference.child("employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                snapshot.getChildren();


                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    Map childItem = (Map) dataSnapshot.getValue();
                    childItem.get("name");
                    childItem.get("salary");
                    Map address= (Map) childItem.get("address");
                    address.get("area");
                    address.get("pincode");


                    names.add((String) childItem.get("name"));

                }





//                snapshot.g

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (nameEt.getText().toString().trim().length() > 0) {

                    String key = databaseReference.push().getKey();

                    assert key != null;
                    databaseReference.child(key).child("name").setValue(nameEt.getText().toString().trim());
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();


                }
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //    users/u2/name
                databaseReference.child("e1").child("address").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Map values = (Map) snapshot.getValue();

                        String area1 = (String) values.get("area");
                        readTv.setText(area1);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.child("e1").child("address").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


    }
}