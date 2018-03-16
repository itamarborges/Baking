package com.example.itamarborges.baking;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.itamarborges.baking.model.RecipeModel;
import com.example.itamarborges.baking.pojo.Recipe;
import com.example.itamarborges.baking.pojo.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private Recipe currentRecipe;

    StepFullDescriptionFragment stepFragment;

    @BindView(R.id.btn_previous)
    Button btnPrevious;

    @BindView(R.id.btn_stop)
    Button btnStop;

    @BindView(R.id.btn_next)
    Button btnNex;


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

        currentRecipe = RecipeModel.getRecipe(idRecipe);
        setCurrentStep();
    }

    private void nextStep() {
        idStep++;
        setCurrentStep();
    }

    private void previousStep() {
        idStep--;
        setCurrentStep();
    }

    private void setCurrentStep() {
        currentStep = RecipeModel.getStep(idRecipe, idStep);
        stepFragment = (StepFullDescriptionFragment) getSupportFragmentManager().findFragmentById(R.id.step_fragment);
        stepFragment.setStep(currentStep);

        showButtons();
    }

    @OnClick(R.id.btn_previous)
    public void previousStep(View view) {
        previousStep();
    }

    @OnClick(R.id.btn_stop)
    public void stopStep(View view) {
        finish();
    }

    @OnClick(R.id.btn_next)
    public void nextStep(View view) {
        nextStep();
    }


    private void showButtons() {
        btnPrevious.setVisibility((idStep < 1) ? View.INVISIBLE : View.VISIBLE);
        btnNex.setVisibility(idStep + 1 == currentRecipe.getSteps().size() ? View.INVISIBLE : View.VISIBLE);
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

        setCurrentStep();
    }
}
