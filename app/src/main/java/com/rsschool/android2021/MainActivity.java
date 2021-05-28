package com.rsschool.android2021;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements ActionEventListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    @Override
    public void onBackPressed() {
        final Fragment currentFragment = fragmentManager.findFragmentById(R.id.container);
        if (currentFragment instanceof SecondFragment) {
            SecondFragment fragment = (SecondFragment) currentFragment;
            fragment.onActivityAttach();
        }
        else
            super.onBackPressed();
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, firstFragment).commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, secondFragment, "SecondFragment").commit();
    }

    @Override
    public void onFirstFragmentActionEvent(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onSecondFragmentActionEvent(int previousNumber) {
        openFirstFragment(previousNumber);
    }
}
