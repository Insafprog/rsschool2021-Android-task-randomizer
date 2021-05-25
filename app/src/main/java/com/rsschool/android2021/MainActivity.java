package com.rsschool.android2021;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ActionEventListener {
    private int min = 0;
    private int max = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0, 0, 0);
    }

    private void openFirstFragment(int previousNumber, int min, int max) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber, min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
    }

    @Override
    public void onFirstFragmentActionEvent(int min, int max) {
        this.min = min;
        this.max = max;
        openSecondFragment(min, max);
    }

    @Override
    public void onSecondFragmentActionEvent(int previousNumber) {
        openFirstFragment(previousNumber, min, max);
    }
}
