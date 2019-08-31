package com.example.attendance_face;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(Context context, List<Student> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }
        Student student = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.primary_location);
        TextView roll= (TextView) convertView.findViewById((R.id.location_offset));
        TextView batch = (TextView) convertView.findViewById(R.id.date);
        TextView index = (TextView) convertView.findViewById((R.id.magnitude));
        assert student != null;
        String mag =  formatMagnitude((student).getIndex());
        index.setText(mag);
        name.setText(student.getName());
        roll.setText(student.getRoll());
        batch.setText(String.valueOf(student.getBatch()));
        GradientDrawable magnitudeCircle = (GradientDrawable) index.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(student.getIndex());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return convertView;

    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
    private int getMagnitudeColor(double magni) {
        int  magn = (int) Math.floor( magni);

        switch (magn){
            case 9:
            case 8:
                return ContextCompat.getColor(getContext(), R.color.magnitude1);
            case 7:
                return ContextCompat.getColor(getContext(), R.color.magnitude2);
            case 6:
                return ContextCompat.getColor(getContext(), R.color.magnitude3);
            case 5:
                return ContextCompat.getColor(getContext(), R.color.magnitude4);
            case 4:
                return ContextCompat.getColor(getContext(), R.color.magnitude5);
            case 3:
                return ContextCompat.getColor(getContext(), R.color.magnitude6);
            case 2:
                return ContextCompat.getColor(getContext(), R.color.magnitude7);
            case 1:
                return ContextCompat.getColor(getContext(), R.color.magnitude8);
            case 0:
                return ContextCompat.getColor(getContext(), R.color.magnitude9);
            default:
                return ContextCompat.getColor(getContext(), R.color.magnitude10plus);

        }
    }
}