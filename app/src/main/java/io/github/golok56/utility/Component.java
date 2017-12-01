package io.github.golok56.utility;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Component {

    private Component(){}

    public static void setText(AppCompatActivity activity, int id, String text){
        TextView tv = (TextView) activity.findViewById(id);
        tv.setText(text);
    }

    public static void setText(View view, int id, String text){
        TextView tv = (TextView) view.findViewById(id);
        tv.setText(text);
    }

    public static String getText(AppCompatActivity activity, int id){
        TextView tv = (TextView) activity.findViewById(id);
        return tv.getText().toString();
    }

    public static String getText(View view, int id){
        TextView tv = (TextView) view.findViewById(id);
        return tv.getText().toString();
    }

    public static void setOnClickListener(AppCompatActivity activity, int id, View.OnClickListener listener){
        Button btn = (Button) activity.findViewById(id);
        btn.setOnClickListener(listener);
    }

    public static void setChecked(AppCompatActivity activity, int id, boolean value){
        RadioButton rb = (RadioButton) activity.findViewById(id);
        rb.setChecked(value);
    }

    public static boolean isChecked(AppCompatActivity activity, int id){
        RadioButton rb = (RadioButton) activity.findViewById(id);
        return rb.isChecked();
    }

    public static void setSpinner(View view, int id, ArrayAdapter adapter, AdapterView.OnItemSelectedListener listener){
        Spinner spinner = (Spinner) view.findViewById(id);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(listener);
    }

}
