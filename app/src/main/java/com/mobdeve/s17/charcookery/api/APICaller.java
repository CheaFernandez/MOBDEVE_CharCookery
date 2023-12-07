package com.mobdeve.s17.charcookery.api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICaller {

    public interface APICallback<T> {
        void onSuccess(T result);

        default void onFailure(Throwable t) {
            // Default behavior for failure
            t.printStackTrace();
        }
    }

    public static <T> void enqueue(Call<T> call, APICallback<T> apiCallback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    apiCallback.onSuccess(response.body());
                } else {
                    // Handle unsuccessful response
                    // Pass more information to the callback if needed
                    apiCallback.onFailure(new Exception("API call failed with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                // Handle network errors or other failures
                apiCallback.onFailure(t);
            }
        });
    }
}
