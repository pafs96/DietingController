package com.example.przemeksokolowski.dietingcontroller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.przemeksokolowski.dietingcontroller.data.ApiUtils;
import com.example.przemeksokolowski.dietingcontroller.data.Preferences;
import com.example.przemeksokolowski.dietingcontroller.data.WebAPI;
import com.example.przemeksokolowski.dietingcontroller.model.ChoosenProductsUsedToGetMeals;
import com.example.przemeksokolowski.dietingcontroller.model.MealWithChoosenProducts;
import com.example.przemeksokolowski.dietingcontroller.model.Workout;
import com.github.clans.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import at.grabner.circleprogress.CircleProgressView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private WebAPI mWebAPI;

    private ProgressBar mLoadingIndicator;
    private ConstraintLayout mConstraintLayout;
    private CircleProgressView mProgressView;

    private int mLoggedUserId, mLimit, mCurrentCalories;
    private String mCurrentDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mLoggedUserId = ApiUtils.getUserIdFromIntent(getIntent());
        mLimit = Preferences.getDailyLimit(MainActivity.this);
        mCurrentDay = Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH;

        mConstraintLayout = findViewById(R.id.main_constraint);
        mLoadingIndicator = findViewById(R.id.pb_loading_main_indicator);
        mProgressView = findViewById(R.id.progressview);

        showLoading();

        mWebAPI = ApiUtils.getWebApi();
        mWebAPI.getMealsByUserIdAndTime(mLoggedUserId, mCurrentDay).enqueue(mealsCallback);

        FloatingActionButton newExerciseFab = findViewById(R.id.new_exercise_fab);
        FloatingActionButton newMealFab = findViewById(R.id.new_meal_fab);
        newExerciseFab.setOnClickListener(clickListener);
        newMealFab.setOnClickListener(clickListener);
    }

    private void showLoading() {
        mConstraintLayout.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mConstraintLayout.setVisibility(View.VISIBLE);
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

    private Callback<List<MealWithChoosenProducts>> mealsCallback = new Callback<List<MealWithChoosenProducts>>() {
        @Override
        public void onResponse(@NonNull Call<List<MealWithChoosenProducts>> call, @NonNull Response<List<MealWithChoosenProducts>> response) {
            if (response.isSuccessful()) {
                List<MealWithChoosenProducts> meals = response.body();

                for (MealWithChoosenProducts meal : meals) {
                    for (ChoosenProductsUsedToGetMeals product : meal.getChoosenProducts()) {
                        mCurrentCalories += (product.getWeight() * product.getProduct().getCalories() / 100);
                    }
                }

                mWebAPI.getActivitiesByUserIdAndTime(mLoggedUserId, mCurrentDay).enqueue(activitiesCallback);

            } else {
                Log.d("MealsCallback", "Code: " + response.code() + " Message: " + response.message());
                ApiUtils.noApiConnectionDialog(getApplicationContext(), getParent());
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<MealWithChoosenProducts>> call, @NonNull Throwable t) {
            t.printStackTrace();
            ApiUtils.noApiConnectionDialog(getApplicationContext(), getParent());
        }
    };

    private Callback<List<Workout>> activitiesCallback = new Callback<List<Workout>>() {
        @Override
        public void onResponse(@NonNull Call<List<Workout>> call, @NonNull Response<List<Workout>> response) {
            if (response.isSuccessful()) {
                List<Workout> workouts = response.body();

                for (Workout workout : workouts) {
                    //TODO(4) w zależności od api - odejmowanie aktywności od mCurrentCalories
                }

                showDataView();
                mProgressView.setValueAnimated(mCurrentCalories / mLimit, 1800);
            } else {
                Log.d("ActivitiesCallback", "Code: " + response.code() + " Message: " + response.message());
                ApiUtils.noApiConnectionDialog(getApplicationContext(), getParent());
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Workout>> call, @NonNull Throwable t) {
            t.printStackTrace();
            ApiUtils.noApiConnectionDialog(getApplicationContext(), getParent());
        }
    };
}
