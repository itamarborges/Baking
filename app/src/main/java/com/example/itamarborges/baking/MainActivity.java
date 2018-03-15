package com.example.itamarborges.baking;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.itamarborges.baking.adapter.RecipeAdapter;
import com.example.itamarborges.baking.model.RecipeModel;
import com.example.itamarborges.baking.pojo.Recipe;
import com.example.itamarborges.baking.utils.JsonUtils;
import com.example.itamarborges.baking.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    private static final int RECIPES_LOADER_ID = 0;

    @BindView(R.id.rv_recipes)
    RecyclerView mRvRecipes;

    private GridLayoutManager mLayoutManager;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLayoutManager = new GridLayoutManager(this, Utils.numberOfColumns(getWindowManager()));
        mRvRecipes.setLayoutManager(mLayoutManager);

        mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>());
        mRvRecipes.setAdapter(mRecipeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(RECIPES_LOADER_ID, null, this);
    }


    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Object>(this) {

            List<Recipe> mRecipes = null;

            @Override
            protected void onStartLoading() {
                if (mRecipes != null) {
                    deliverResult(mRecipes);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Recipe> loadInBackground() {
                List<Recipe> mRecipes = new ArrayList<>();
                String jSonRecipes = null;
                try {
                    jSonRecipes = JsonUtils.loadJSONFromAsset(getContext());

                    mRecipes = RecipeModel.getAllRecipes(jSonRecipes);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return mRecipes;
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(List<Recipe> data) {
                mRecipes = data;
                super.deliverResult(data);
            }
        };
    }


    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        mRecipeAdapter.setRecipes((List<Recipe>)data);
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}
