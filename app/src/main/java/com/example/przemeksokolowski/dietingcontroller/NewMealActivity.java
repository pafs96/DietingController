package com.example.przemeksokolowski.dietingcontroller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.przemeksokolowski.dietingcontroller.data.ApiUtils;
import com.example.przemeksokolowski.dietingcontroller.model.ChoosenProducts;
import com.example.przemeksokolowski.dietingcontroller.model.Meal;
import com.example.przemeksokolowski.dietingcontroller.model.Product;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMealActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int mealType = 1, mLoggedUserId;

    private ArrayList<Product> products;
    private ArrayList<ChoosenProducts> choosenProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meal);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLoggedUserId = ApiUtils.getUserIdFromIntent(getIntent());

        Spinner spinner = findViewById(R.id.meal_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.meal_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.products_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                choosenProducts.remove(viewHolder.getAdapterPosition());
                recyclerViewAdapter.swapList(choosenProducts);
            }
        }).attachToRecyclerView(recyclerView);


        Button addProductBtn = findViewById(R.id.add_product_button);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO(2) Dodawanie produktów (do arraylisty wraz z aktualizacją recyclerview)
            }
        });

        Button saveMealBtn = findViewById(R.id.add_meal_button);
        saveMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMealToAPI();
            }
        });
    }

    private void postMealToAPI() {
        ApiUtils.getWebApi().createMeal(mLoggedUserId, mealType).enqueue(new Callback<Meal>() {
            @Override
            public void onResponse(@NonNull Call<Meal> call, @NonNull Response<Meal> response) {
                if (response.isSuccessful()) {
                    int addedMealId = response.body().getId();
                    for (ChoosenProducts product : choosenProducts) {
                        postProductToAPI(product);
                    }

                    Log.i("postMealToAPI", "POST submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meal> call, @NonNull Throwable t) {
                Log.e("postMealToAPI", "Unable to submit post to API.");
                ApiUtils.noApiConnectionDialog(NewMealActivity.this, getParent());
            }
        });
    }

    private void postProductToAPI(ChoosenProducts product) {
        ApiUtils.getWebApi().createChoosenProduct(product).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i("postMealToAPI", "POST submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("postMealToAPI", "Unable to submit post to API.");
                ApiUtils.noApiConnectionDialog(NewMealActivity.this, getParent());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_history:
                startActivity(new Intent(NewMealActivity.this, HistoryActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(NewMealActivity.this, SettingsActivity.class));
                return true;
                //TODO(11) Dodawanie produktów
            //case R.id.action_new_product:
            //startActivity(new Intent(NewMealActivity.this, HistoryActivity.class));
            //return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        mealType = pos + 1;
    }

    public void onNothingSelected(AdapterView<?> parent) {
        mealType = 1;
    }
}
