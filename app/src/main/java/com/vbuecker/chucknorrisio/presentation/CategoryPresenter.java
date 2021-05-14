package com.vbuecker.chucknorrisio.presentation;

import com.vbuecker.chucknorrisio.Colors;
import com.vbuecker.chucknorrisio.MainActivity;
import com.vbuecker.chucknorrisio.datasource.CategoryRemoteDataSource;
import com.vbuecker.chucknorrisio.model.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryPresenter implements CategoryRemoteDataSource.ListCategoriesCallback {

    private final MainActivity view;
    private final CategoryRemoteDataSource dataSource;

    /* DADOS FAKE
    static {
        fakeResponse.add(new CategoryItem("Cat1", 0x1F442CBA));
        fakeResponse.add(new CategoryItem("Cat2", 0xEB73C8C8));
        fakeResponse.add(new CategoryItem("Cat3", 0xF442CBAB));
        fakeResponse.add(new CategoryItem("Cat4", 0xEB73C8C8));
        fakeResponse.add(new CategoryItem("Cat5", 0xFF00FBFF));
        fakeResponse.add(new CategoryItem("Cat6", 0xFF00FDBF));
        fakeResponse.add(new CategoryItem("Cat7", 0xFF00FFFF));
        fakeResponse.add(new CategoryItem("Cat8", 0xFF05FFFB));
    }
*/


    public CategoryPresenter(MainActivity mainActivity, CategoryRemoteDataSource dataSource) {
        this.view = mainActivity;
        this.dataSource = dataSource;
    }

    public void requestAll() {
        //chamar um servidor http
        //this.request();
        this.view.showProgressBar();
        this.dataSource.findAll(this);
    }

    public void onSucess(List<String> response) {
        List<CategoryItem> categoryItems = new ArrayList<>();
        for (String val : response) {
            categoryItems.add(new CategoryItem(val, Colors.randomColor()));
        }
        view.showCategories(categoryItems);
    }

    @Override
    public void onError(String message) {
        this.view.showFailure(message);
    }

    public void onComplete() {
        view.hideProgessBar();

    }
}

   /*
      MÉTODO FAKE
      private void request() {
    */
       /* //SIMULANDO UM ERRO:
        new Handler().postDelayed(()->{
            try {
                throw new Exception("falha no servidor");
            } catch (Exception e) {
                onError(e.getMessage()); //caso dê execeção, pega a msg do erro.
            }finally {
                onCompleted();
            }
        },5000 );

        //Sucesso:

        new Handler().postDelayed(() -> {
            onSucess(fakeResponse);
           onCompleted();
           }, 5000);
    }
}*/
