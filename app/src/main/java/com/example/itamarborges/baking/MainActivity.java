package com.example.itamarborges.baking;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.itamarborges.baking.adapter.RecipeAdapter;
import com.example.itamarborges.baking.idlingResource.SimpleIdlingResource;
import com.example.itamarborges.baking.model.RecipeModel;
import com.example.itamarborges.baking.pojo.Recipe;
import com.example.itamarborges.baking.utils.JsonUtils;
import com.example.itamarborges.baking.utils.NetworkUtils;
import com.example.itamarborges.baking.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>, RecipeAdapter.Done {

    private static final int RECIPES_LOADER_ID = 0;

    private static final String RECIPES_INDEX = "recipeIdIndex";

    @BindView(R.id.txt_no_connection)
    TextView mTxtNoConnection;

    @BindView(R.id.btn_try_again)
    Button mBtnTryAgain;

    @BindView(R.id.rv_recipes)
    RecyclerView mRvRecipes;

    private GridLayoutManager mLayoutManager;
    private RecipeAdapter mRecipeAdapter;

    ArrayList<Recipe> mAllRecipes = null;

    @Nullable
    public SimpleIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    public void setIdlingResource(@Nullable SimpleIdlingResource mIdlingResource) {
        this.mIdlingResource = mIdlingResource;
    }

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLayoutManager = new GridLayoutManager(this, Utils.numberOfColumns(getWindowManager()));
        mRvRecipes.setLayoutManager(mLayoutManager);

        mRecipeAdapter = new RecipeAdapter(this, new ArrayList<Recipe>());
        mRvRecipes.setAdapter(mRecipeAdapter);

        getIdlingResource();
        getRecipes();
    }

    private void getRecipes() {
        if (mAllRecipes == null) {
            if (NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                getSupportLoaderManager().initLoader(RECIPES_LOADER_ID, null, this);
                showRecipes();
            } else {
                showTryAgain();
            }
        } else {
            showRecipes();
        }
    }

    private void showTryAgain() {
        mRvRecipes.setVisibility(View.INVISIBLE);
        mTxtNoConnection.setVisibility(View.VISIBLE);
        mBtnTryAgain.setVisibility(View.VISIBLE);
    }

    private void showRecipes() {
        mRvRecipes.setVisibility(View.VISIBLE);
        mTxtNoConnection.setVisibility(View.INVISIBLE);
        mBtnTryAgain.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRecipes();
    }


    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Object>(this) {

            List<Recipe> mRecipes = null;

            @Override
            protected void onStartLoading() {

                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(false);
                }
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

                    mRecipes = RecipeModel.getAllRecipes();

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
        mAllRecipes = (ArrayList<Recipe>)data;
        mRecipeAdapter.setRecipes(mAllRecipes);
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @Override
    public void onDone() {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
    }

    @OnClick(R.id.btn_try_again)
    public void tryAgain() {
        getRecipes();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_INDEX, mAllRecipes);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(RECIPES_INDEX)) {
            mAllRecipes = savedInstanceState.getParcelableArrayList(RECIPES_INDEX);
            mRecipeAdapter.setRecipes(mAllRecipes);
        }
    }
}
