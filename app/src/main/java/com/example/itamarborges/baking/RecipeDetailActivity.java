package com.example.itamarborges.baking;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.itamarborges.baking.model.RecipeModel;
import com.example.itamarborges.baking.pojo.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String INTENT_KEY_ID = "stepId";
    public static final String INTENT_KEY_RECIPE_ID = "recipeId";
    public static final String INTENT_KEY_RECIPE_NAME = "recipeName";

    private static final String RECIPE_NAME = "recipeName";
    private static final String STEP_ID = "stepId";
    private static final String RECIPE_ID = "recipeId";

    private String recipeName;
    private int idStep = -1;
    private int idRecipe = -1;

    private Step currentStep;

    StepFullDescriptionFragment stepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        if (idStep == -1) {
            idStep = getIntent().getIntExtra(INTENT_KEY_ID, -1);
            idRecipe = getIntent().getIntExtra(INTENT_KEY_RECIPE_ID, -1);
            recipeName = getIntent().getStringExtra(INTENT_KEY_RECIPE_NAME);
        }

        setTitle(recipeName);

        currentStep = RecipeModel.getStep(idRecipe, idStep);
        stepFragment = (StepFullDescriptionFragment) getSupportFragmentManager().findFragmentById(R.id.step_fragment);
        stepFragment.setStep(currentStep);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_ID, idStep);
        outState.putString(RECIPE_NAME, recipeName);
        outState.putInt(RECIPE_ID, idRecipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(STEP_ID)) {
            idStep = savedInstanceState.getInt(STEP_ID);
        }
        if (savedInstanceState.containsKey(RECIPE_ID)) {
            idRecipe = savedInstanceState.getInt(RECIPE_ID);
        }
        if (savedInstanceState.containsKey(RECIPE_NAME)) {
            recipeName = savedInstanceState.getString(RECIPE_NAME);
        }
    }
}
