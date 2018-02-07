package com.example.przemeksokolowski.dietingcontroller;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingIndicator;
    private ConstraintLayout mConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton newExerciseFab = findViewById(R.id.new_exercise_fab);
        FloatingActionButton newMealFab = findViewById(R.id.new_meal_fab);
        newExerciseFab.setOnClickListener(clickListener);
        newMealFab.setOnClickListener(clickListener);

        mConstraintLayout = findViewById(R.id.main_constraint);
        mLoadingIndicator = findViewById(R.id.pb_loading_main_indicator);
        showLoading();
    }

    private void showLoading() {
        mConstraintLayout.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.new_exercise_fab:
                    startActivity(new Intent(MainActivity.this, NewExerciseActivity.class));
                    break;
                case R.id.new_meal_fab:
                    startActivity(new Intent(MainActivity.this, NewMealActivity.class));
                    break;
            }
        }
    };
}
