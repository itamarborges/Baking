package com.example.itamarborges.baking.model;

import com.example.itamarborges.baking.pojo.Ingredient;
import com.example.itamarborges.baking.pojo.Recipe;
import com.example.itamarborges.baking.pojo.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itamarborges on 13/03/18.
 */

public class RecipeModel {

    private static List<Recipe> mRecipes = new ArrayList<>();

    public static List<Recipe> getAllRecipes(String jsonString) throws JSONException {

        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_IMAGE = "image";

        final String INGREDIENT_QUANTITY = "quantity";
        final String INGREDIENT_MEASURE = "measure";
        final String INGREDIENT_INGREDIENT = "ingredient";

        final String STEP_ID = "id";
        final String STEP_SHORT_DESCRIPTION = "shortDescription";
        final String STEP_DESCRIPTION = "description";
        final String STEP_VIDEO_URL = "videoURL";
        final String STEP_THUMBNAIL_URL = "thumbnailURL";

        if (mRecipes.size() == 0) {

            JSONArray recipesJson = new JSONArray(jsonString);

            for (int i = 0; i < recipesJson.length(); i++) {
                List<Ingredient> mIngredients = new ArrayList<>();
                List<Step> mSteps = new ArrayList<>();

                JSONObject recipeJson = recipesJson.getJSONObject(i);

                JSONArray ingredientsArray = recipeJson.getJSONArray(RECIPE_INGREDIENTS);
                for (int j = 0; j < ingredientsArray.length(); j++) {
                    JSONObject ingredientJson = ingredientsArray.getJSONObject(j);

                    Double quantity = ingredientJson.getDouble(INGREDIENT_QUANTITY);
                    String measure = ingredientJson.getString(INGREDIENT_MEASURE);
                    String ingredient = ingredientJson.getString(INGREDIENT_INGREDIENT);

                    Ingredient ingr = new Ingredient(quantity, measure, ingredient);

                    mIngredients.add(ingr);
                }

                JSONArray stepsArray = recipeJson.getJSONArray(RECIPE_STEPS);
                for (int j = 0; j < stepsArray.length(); j++) {
                    JSONObject stepJson = stepsArray.getJSONObject(j);

                    int id = stepJson.getInt(STEP_ID);
                    String shortDescription = stepJson.getString(STEP_SHORT_DESCRIPTION);
                    String description = stepJson.getString(STEP_DESCRIPTION);
                    String videoURL = stepJson.getString(STEP_VIDEO_URL);
                    String thumbnail = stepJson.getString(STEP_THUMBNAIL_URL);

                    Step step = new Step(id, shortDescription, description, videoURL, thumbnail);

                    mSteps.add(step);
                }

                int id = recipeJson.getInt(RECIPE_ID);
                String name = recipeJson.getString(RECIPE_NAME);
                int servings = recipeJson.getInt(RECIPE_SERVINGS);
                String image = recipeJson.getString(RECIPE_IMAGE);


                Recipe recipe = new Recipe(id, name, mIngredients, mSteps, servings, image);

                mRecipes.add(recipe);
            }
        }

        return mRecipes;
    }

    public static Recipe getRecipe(int id){

        for (Recipe r: mRecipes) {
            if (r.getId() == id) {
             return r;
            }
        }
        return null;
    }
}
