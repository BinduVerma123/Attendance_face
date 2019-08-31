package com.example.attendance_face;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mStudentDatabaseReference;
    private StudentAdapter mStudentAdapter;
    private ChildEventListener mChildEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance();
        mStudentDatabaseReference=mDatabase.getReference().child("student");
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        List<Student> Students = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDatabase=FirebaseDatabase.getInstance();
        mStudentDatabaseReference=mDatabase.getReference().child("student");
        mStudentAdapter = new StudentAdapter(this,Students);
        earthquakeListView.setAdapter(mStudentAdapter);
        attachDatabaseListener();
        ProgressBar mProgressBar= findViewById(R.id.progress_circular);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    }
    private void attachDatabaseListener() {
        if(mChildEventListener==null){
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Student student =
                            dataSnapshot.getValue(Student.class);
                    mStudentAdapter.add(student);
                    mStudentAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mStudentDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }
}
