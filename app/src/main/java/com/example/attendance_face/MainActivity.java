package com.example.attendance_face;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String ANONYMOUS = "anonymous";
    public static final int RC_SIGN_IN = 123;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mStudentDatabaseReference;
    private StudentAdapter mStudentAdapter;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String mUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mUsername = ANONYMOUS;



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
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){
                    onSignedInitialise(user.getDisplayName());
                }
                else{
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()

                                    .setAvailableProviders(Collections.singletonList(

                                            new AuthUI.IdpConfig.GoogleBuilder().build()
                                    ))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };
    }
    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        mStudentAdapter.clear();
        detachDatabseListener();
    }

    private void onSignedInitialise(String username) {
        mUsername = username;
        attachDatabaseListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== RC_SIGN_IN){
            if(resultCode== RESULT_OK){
                Toast.makeText(this,"Signed in", Toast.LENGTH_SHORT).show();
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Sign in cancelled!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        detachDatabseListener();
        mStudentAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
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
    private void detachDatabseListener() {
        if (mChildEventListener != null) {
            mStudentDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener=null;   }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
