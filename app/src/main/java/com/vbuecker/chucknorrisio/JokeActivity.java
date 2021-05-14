package com.vbuecker.chucknorrisio;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vbuecker.chucknorrisio.datasource.JokeRemoteDataSource;
import com.vbuecker.chucknorrisio.model.Joke;
import com.vbuecker.chucknorrisio.presentation.JokePresenter;

public class JokeActivity extends AppCompatActivity {

    static final String CATEGORY_KEY = "category_key";
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            String category = getIntent().getExtras().getString(CATEGORY_KEY);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(category);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                JokeRemoteDataSource dataSource = new JokeRemoteDataSource();
                final JokePresenter presenter = new JokePresenter(this, dataSource);
                presenter.findJokeBy(category);

                FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(view -> {
                    presenter.findJokeBy(category);
                });
            }
        }
    }

    public void showJoke(Joke joke){
        TextView txtJoke = findViewById(R.id.txt_joke);
        txtJoke.setText(joke.getValue());

       ImageView img = findViewById(R.id.img_icon);
       Picasso.get().load(joke.getIconUrl()).into(img);
    }

    public void showFailure (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showProgressBar(){
        if(progress == null){
            progress = new ProgressDialog(this);
            progress.setMessage("Carregando...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
        }
        progress.show();
    }

    public void hideProgessBar(){
        if(progress != null){
            progress.hide();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return true;

    }
}