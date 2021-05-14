package com.vbuecker.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;

import com.vbuecker.chucknorrisio.model.Joke;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeRemoteDataSource {

    public interface JokeCallback {

        void onSucess(Joke response);

        void onError(String message);

        void onComplete();

    }

    public void findJokeBy(JokeCallback callback, String category) {
       // new JokeTask(callback, category).execute();
        HttpClient.retrofit().create(ChuckNorrisApi.class)
                .findRandomBy(category)
                .enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                    if (response.isSuccessful())
                        callback.onSucess(response.body());
                        callback.onComplete();
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                    callback.onError(t.getMessage());
                    callback.onComplete();
                    }
                });
    }

    private static class JokeTask extends AsyncTask <Void, Void, Joke> {

        private final JokeCallback callback;
        private final String category;

        private String errorMessage;

        public JokeTask(JokeCallback callback, String category) {
            this.callback = callback;
            this.category = category;
        }

        @Override
        protected Joke doInBackground(Void... voids) {

            Joke joke = null;
            HttpsURLConnection urlConnection;

            try {
                String endpoint = String.format("%s?category=%s", Endpoint.GET_JOKE, category);
                URL url = new URL(endpoint);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(2000);
                urlConnection.setReadTimeout(2000);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode > 400) {
                    throw new IOException("Erro no servidor!");
                }
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                JsonReader jsonReader = new JsonReader(new InputStreamReader(in)); //transforma o dado em json

                // parser do json
                jsonReader.beginObject();
                String iconUrl = null;
                String value = null;

                while (jsonReader.hasNext()) {
                    JsonToken token = jsonReader.peek();

                    if (token == JsonToken.NAME) {
                        String name = jsonReader.nextName();

                        if (name.equals("category"))
                            jsonReader.skipValue(); //pula e vai pro proximo

                        else if (name.equals("icon_url"))
                            jsonReader.nextString();

                        else if (name.equals("value"))
                            jsonReader.nextString();

                        else
                            jsonReader.skipValue();
                    }
                }
                joke = new Joke(iconUrl, value);
                jsonReader.endObject();


            } catch (MalformedURLException e) {
                errorMessage = e.getMessage();
            } catch (IOException e) {
                errorMessage = e.getMessage();
            }

            return joke;

        }

        @Override
        protected void onPostExecute(Joke joke) {
            if (errorMessage != null) {
                callback.onError(errorMessage);
            } else {
                callback.onSucess(joke);
            }
            callback.onComplete();
        }
    }

}
