package io.github.golok56.utility;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

public class IntentUtil {

    public static final String CONTENT = "content";
    private static final String EDIT = "edit";

    public static void start(AppCompatActivity activity, Class<?> cls, boolean finish){
        Intent intent = new Intent(activity, cls);
        startIntent(activity, intent, finish);
    }

    public static void start(AppCompatActivity activity, Class<?> cls, boolean finish, int flags){
        Intent intent = new Intent(activity, cls);
        intent.setFlags(flags);
        startIntent(activity, intent, finish);
    }

    public static void start(AppCompatActivity activity, Class<?> cls, boolean finish, boolean isEdit){
        Intent intent = new Intent(activity, cls);
        intent.putExtra(EDIT, isEdit);
        startIntent(activity, intent, finish);
    }

    public static void start(AppCompatActivity activity, Class<?> cls, boolean finish, Parcelable content){
        Intent intent = new Intent(activity, cls);
        intent.putExtra(CONTENT, content);
        startIntent(activity, intent, finish);
    }

    public static void start(AppCompatActivity activity, Class<?> cls, boolean finish, boolean isEdit, Parcelable content){
        Intent intent = new Intent(activity, cls);
        intent.putExtra(EDIT, isEdit);
        intent.putExtra(CONTENT, content);
        startIntent(activity, intent, finish);
    }

    private static void startIntent(AppCompatActivity activity, Intent intent, boolean finish){
        activity.startActivity(intent);
        if(finish) activity.finish();
    }

}
