package com.example.przemeksokolowski.dietingcontroller.data;

import com.example.przemeksokolowski.dietingcontroller.model.ChoosenProducts;
import com.example.przemeksokolowski.dietingcontroller.model.Meal;
import com.example.przemeksokolowski.dietingcontroller.model.Product;
import com.example.przemeksokolowski.dietingcontroller.model.User;
import com.example.przemeksokolowski.dietingcontroller.model.Workout;
import com.example.przemeksokolowski.dietingcontroller.model.WorkoutType;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebAPI {

    String BASE_URL = "http://localhost:3000/";


    /*
    Sessions methods
     */
    @POST("login")
    Call<ResponseBody> login();

    @DELETE("logout")
    Call<ResponseBody> delete();


    /*
    Lookup methods
     */
    @GET("lookup")
    Call<User> getLookup();


    /*
    Product methods
     */
    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int productId);

    @POST("products")
    Call<ResponseBody> createProduct(@Body Product product);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProductById(@Path("id") int productId, @Body Product product);

    @PATCH("products/{id}")
    Call<ResponseBody> updateProductById(@Path("id") int productId);


    /*
    Meals methods
    */
    @GET("meals/{id}")
    Call<Meal> getMealById(@Path("id") int mealId);

    @POST("meals")
    Call<ResponseBody> createMeal(@Body Meal meal);

    @DELETE("meals/{id}")
    Call<ResponseBody> deleteMealById(@Path("id") int mealId);

    @PATCH("meals/{id}")
    Call<ResponseBody> updateMealById(@Path("id") int mealId, @Body Meal meal);



    /*
    Chosen products methods
     */
    @GET("choosen_products/{id}")
    Call<ChoosenProducts> getChoosenProductById(@Path("id") int choosenProductId);

    @POST("choosen_products")
    Call<ResponseBody> createChoosenProduct(@Body ChoosenProducts choosenProducts);

    @DELETE("choosen_products/{id}")
    Call<ResponseBody> deleteChoosenProductById(@Path("id") int choosenProductId);

    @PATCH("choosen_products/{id}")
    Call<ResponseBody> updateChoosenProductById(@Path("id") int choosenProductId, @Body ChoosenProducts choosenProduct);


    /*
    User methods.
    */
    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int userId);

    @POST("users")
    Call<ResponseBody> createUser(@Body User user);

    @DELETE("users/{id}")
    Call<ResponseBody> deleteUserById(@Path("id") int userId);

    @PATCH("users/{id}")
    Call<ResponseBody> updateUserById(@Path("id") int userId, @Body User user);

    /*
    Activity types methods.
    */
    @GET("activity_types")
    Call<List<WorkoutType>> getAllWorkoutTypes();

    @GET("activity_types/{id}")
    Call<WorkoutType> getWorkoutTypesById(@Path("id") int workoutTypeId);

    @POST("activity_types")
    Call<ResponseBody> createWorkoutTypeById(@Body WorkoutType workoutType);

    @DELETE("activity_types/{id}")
    Call<ResponseBody> deleteWorkoutTypeById(@Path("id") int workoutTypeId);

    @PATCH("activity_types/{id}")
    Call<ResponseBody> updateWorkoutTypeById(@Path("id") int workoutTypeId, @Body WorkoutType workoutType);


    /*
    Activities methods.
    */
    @GET("activities")
    Call<List<Workout>> getAllWorkouts();

    @POST("activities/{id}")
    Call<ResponseBody> createWorkoutById(@Body Workout workoutId);

    @DELETE("activities/{id}")
    Call<ResponseBody> deleteWorkoutById(@Path("id") int workoutId);

    @PATCH("activities/{id}")
    Call<ResponseBody> updateWorkoutById(@Path("id") int workoutId, @Body Workout workout);
}
