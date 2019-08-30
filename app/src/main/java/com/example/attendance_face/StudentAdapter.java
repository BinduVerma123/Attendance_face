package com.example.attendance_face;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(Context context, List<Student> objects) {
        super(context, 0, objects);

    }
}