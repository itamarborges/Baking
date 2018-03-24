package com.example.itamarborges.baking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.example.itamarborges.baking.adapter.IngredientAdapter;
import com.example.itamarborges.baking.adapter.StepAdapter;
import com.example.itamarborges.baking.model.RecipeModel;
import com.example.itamarborges.baking.pojo.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeMasterActivity extends AppCompatActivity {

    public static final String INTENT_KEY_ID = "id";

    private static final String RECIPE_ID_INDEX = "recipeIdIndex";

    private Recipe mRecipe;
    private int idRecipe = -1;

    IngredientAdapter mIngredientAdapter;
    StepAdapter mStepAdapter;

    @BindView(R.id.rv_ingredients)
    RecyclerView mRecyclerIngredients;

    @BindView(R.id.rv_steps_short)
    RecyclerView mRecyclerSteps;

    @Nullable
    @BindView(R.id.frame_video)
    FrameLayout mFrameLayout;

    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_master);
        ButterKnife.bind(this);

        if (idRecipe == -1) {
            if (getIntent() != null && getIntent().hasExtra(INTENT_KEY_ID)){
                idRecipe = getIntent().getIntExtra(INTENT_KEY_ID, -1);
                SharedPreferences preferences = getSharedPreferences(RecipeMasterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("idRecipe", idRecipe);
                editor.commit();
            } else {
                SharedPreferences preferences = getSharedPreferences(RecipeMasterActivity.class.getSimpleName(), Context.MODE_PRIVATE);
                idRecipe = preferences.getInt("idRecipe", -1);
            }
        }

        mRecipe = RecipeModel.getRecipe(idRecipe);

        setTitle(mRecipe.getName());

        mTwoPane = (mFrameLayout != null);

        LinearLayoutManager layoutManagerIngredients = new LinearLayoutManager(this);
        mIngredientAdapter = new IngredientAdapter(mRecipe.getIngredients());
        mRecyclerIngredients.setAdapter(mIngredientAdapter);
        mRecyclerIngredients.setHasFixedSize(false);
        mRecyclerIngredients.setNestedScrollingEnabled(false);
        layoutManagerIngredients.setAutoMeasureEnabled(true);
        mRecyclerIngredients.setLayoutManager(layoutManagerIngredients);

        LinearLayoutManager layoutManagerStep = new LinearLayoutManager(this);
        mStepAdapter = new StepAdapter(mRecipe);
        mStepAdapter.setTwoPane(mTwoPane);
        mRecyclerSteps.setAdapter(mStepAdapter);
        mRecyclerSteps.setHasFixedSize(false);
        mRecyclerSteps.setNestedScrollingEnabled(false);
        layoutManagerStep.setAutoMeasureEnabled(true);
        mRecyclerSteps.setLayoutManager(layoutManagerStep);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt(RECIPE_ID_INDEX, idRecipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(RECIPE_ID_INDEX)) {
            idRecipe = savedInstanceState.getInt(RECIPE_ID_INDEX);
        }
    }
}
