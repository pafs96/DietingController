package com.example.przemeksokolowski.dietingcontroller.data;

import com.example.przemeksokolowski.dietingcontroller.model.ChoosenProducts;
import com.example.przemeksokolowski.dietingcontroller.model.Product;
import com.example.przemeksokolowski.dietingcontroller.model.User;

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

    String BASE_URL = "http://localhost:3000/api/";

    /*
    Lookup methods
     */
    @GET("lookup")
    Call<User> getLookup();


    /*
    User methods.
     */
    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int userId);

    @POST("users")
    Call<ResponseBody> createUser(@Body User user);


    /*
    Product methods
     */
    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int productId);

    @POST("products")
    Call<ResponseBody> createProduct(@Body Product product);


    /*
    Chosen products methods
     */
    @GET("choosen_products/{id}")
    Call<ChoosenProducts> getChoosenProductById(@Path("id") int productId);

    @POST("choosen_products")
    Call<ResponseBody> createChoosenProductById(@Body ChoosenProducts choosenProducts);

    @DELETE("choosen_products/{id}")
    Call<ResponseBody> deleteChoosenProductById(@Path("id") int productId);

    @PATCH("choosen_products/{id}")
    Call<ResponseBody> updateChoosenProductById(@Path("id") int productId, @Body Product product);
}
