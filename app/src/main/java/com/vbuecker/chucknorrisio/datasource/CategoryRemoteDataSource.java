package com.vbuecker.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRemoteDataSource {

    public interface ListCategoriesCallback {

        void onSucess(List<String> response);

        void onComplete();

        void onError(String errorMessage);
    }

    public void findAll(ListCategoriesCallback callback) {
       // new CategoryTask(callback).execute();  NATIVE CALL
        HttpClient.retrofit().create(ChuckNorrisApi.class)
                .findaAll()
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if (response.isSuccessful())
                        callback.onSucess(response.body());
                        callback.onComplete();
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                    callback.onError(t.getMessage());
                    callback.onComplete();
                    }
                });

    }

    private static class CategoryTask extends AsyncTask<Void, Void, List<String>> {


        private final ListCategoriesCallback callback;

        private String errorMessage;

        private CategoryTask(ListCategoriesCallback categoriesCallback) {
            this.callback = categoriesCallback;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {

            List<String> response = new ArrayList<>();
            HttpsURLConnection urlConnection = null;
            try {
                URL url = new URL(Endpoint.GET_CATEGORIES);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(2000);
                urlConnection.setReadTimeout(2000);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode > 400) {
                    throw new IOException("Erro no servidor!");
                }
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JsonReader jsonReader = new JsonReader(new InputStreamReader(in)); //transforma o dado em json

                jsonReader.beginArray();

                while (jsonReader.hasNext()) {
                    response.add(jsonReader.nextString());

                }
                jsonReader.endArray();


            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            if (errorMessage != null) {
                Log.i("TESTE", errorMessage);
                callback.onError(errorMessage);
            }else{
                Log.i("TESTE", strings.toString());
                callback.onSucess(strings);
            }
            callback.onComplete();
        }
    }
}
