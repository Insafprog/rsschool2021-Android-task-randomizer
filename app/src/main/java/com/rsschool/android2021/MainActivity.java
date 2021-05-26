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

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        transaction.replace(R.id.container, firstFragment).commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragmentManager.popBackStack("SecondFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

        transaction.replace(R.id.container, secondFragment).addToBackStack("SecondFragment").commit();
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
