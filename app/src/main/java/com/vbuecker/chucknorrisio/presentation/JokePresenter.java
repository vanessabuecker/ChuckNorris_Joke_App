package com.vbuecker.chucknorrisio.presentation;

import com.vbuecker.chucknorrisio.JokeActivity;
import com.vbuecker.chucknorrisio.datasource.JokeRemoteDataSource;
import com.vbuecker.chucknorrisio.model.Joke;

public class JokePresenter implements JokeRemoteDataSource.JokeCallback {
    private final JokeActivity view;
    private final JokeRemoteDataSource dataSource;

    public JokePresenter(JokeActivity jokeActivity, JokeRemoteDataSource dataSource) {
        this.view = jokeActivity;
        this.dataSource = dataSource;
    }

    public void findJokeBy(String category) {
        this.view.showProgressBar();
        this.dataSource.findJokeBy(this, category);

    }

    @Override
    public void onSucess(Joke response) {
    this.view.showJoke(response);
    }

    @Override
    public void onError(String message) {
    this.view.showFailure(message);
    }

    @Override
    public void onComplete() {
    this.view.hideProgessBar();
    }
}
