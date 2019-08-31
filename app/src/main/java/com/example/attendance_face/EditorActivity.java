package com.example.attendance_face;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Integer.parseInt;

public class EditorActivity extends AppCompatActivity {
    private EditText mNameEdit;
    private EditText mRollEdit;
    private EditText mBatchEdit;
    private Spinner mGenderSpinner;
    private Spinner mSetSpinner;
    public final static int GENDER_UNKNOWN=0;
    public final static int GENDER_MALE=1;
    public final static int GENDER_FEMALE=2;
    private int mGender = GENDER_UNKNOWN;


    private FirebaseDatabase mFirebaseDtabase;
    private DatabaseReference mStudentDatabaseReference;
    //private int mSet = 7;
    private boolean mStudentChanged = false;
    private StudentAdapter mStudentAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();

        mNameEdit = (EditText) findViewById(R.id.edit_name);
        mRollEdit = (EditText) findViewById(R.id.edit_roll);
        mBatchEdit = (EditText) findViewById(R.id.edit_batch);

        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
       // mSetSpinner= (Spinner) findViewById(R.id.spinner_set);
        setupSpinner();
       // mSetSpinner.setOnTouchListener(mTouchListener);
        if(extras==null){
            setTitle("Add a member");
            invalidateOptionsMenu();
        }
        else {
            setTitle("Edit Member");
            mNameEdit.setText(extras.getString("name"));
            mRollEdit.setText(extras.getString("roll"));
           // String sSet =extras.getString("set");
           // int set;
            //if(sSet.compareTo("SET-1")==0)
            //    set=0;
//            else if(sSet.compareTo("SET-2")==0)
//                set=1;
//            else
//                set=2;
//            mSetSpinner.setSelection(set);
        }
        mFirebaseDtabase=FirebaseDatabase.getInstance();

        mStudentDatabaseReference=mFirebaseDtabase.getReference().child("student");
        // mStudentDatabaseReference=mFirebaseDtabase.getReference().child("Date");

        // mStorageReference=mFirebaseStorage.getReference().child("child photos");

        mBatchEdit.setOnTouchListener(mTouchListener);
        mRollEdit.setOnTouchListener(mTouchListener);
        mGenderSpinner.setOnTouchListener(mTouchListener);
        mNameEdit.setOnTouchListener(mTouchListener);

        /*  List<Student> students = new ArrayList<>();
        mStudentAdapter = new StudentAdapter(this, R.layout.list_item, students);
        mListView.setAdapter(mStudentAdapter);*/

    }
    public void addStudents()
    {
        String studentName=mNameEdit.getText().toString();
        String studentRollno=mRollEdit.getText().toString();
        int batch = parseInt(mBatchEdit.getText().toString());
        int gender=mGender;


        if(!TextUtils.isEmpty(studentName) && !TextUtils.isEmpty(studentRollno))
        {
            //DatabaseReference localRef = mStudentDatabaseReference.getRef().child(set);

            Student student=new Student(studentName,studentRollno,gender,batch);
            mStudentDatabaseReference.push().setValue(student);


        }
        else
            Toast.makeText(EditorActivity.this,"Please enter credentials",Toast.LENGTH_SHORT).show();
    }
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mStudentChanged = true;
            return false;
        }
    };
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE;
                    } else {
                        mGender = GENDER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = GENDER_UNKNOWN;
            }
        });
//        ArrayAdapter setSpinnerAdapter = ArrayAdapter.createFromResource(this,
//                R.array.array_set_options, android.R.layout.simple_spinner_item);
//
//        // Specify dropdown layout style - simple list view with 1 item per line
//        setSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//
//        // Apply the adapter to the spinner
//        mSetSpinner.setAdapter(setSpinnerAdapter);
//
//        // Set the integer mSelected to the constant values
//        mSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selection = (String) parent.getItemAtPosition(position);
//                if (!TextUtils.isEmpty(selection)) {
//                    if (selection.equals("SET-1")) {
//                        mSet = 7;
//                    } else if (selection.equals("SET-2")) {
//                        mSet = 8;
//                    } else {
//                        mSet = 9;
//                    }
//                }
//            }
//
//            // Because AdapterView is an abstract class, onNothingSelected must be defined
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                mSet = 7;
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save Student to database
                addStudents();
//                saveStudent();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mStudentChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //    private void saveStudent() {
//
//    }
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteStudent();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteStudent() {
        finish();
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mStudentChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }
}
