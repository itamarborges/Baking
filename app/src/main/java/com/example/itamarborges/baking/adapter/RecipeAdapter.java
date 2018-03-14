package com.example.itamarborges.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itamarborges.baking.R;
import com.example.itamarborges.baking.pojo.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by itamarborges on 14/03/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public void setRecipes(List<Recipe> mRecipes) {
        this.mRecipes = mRecipes;
        this.notifyDataSetChanged();
    }

    private List<Recipe> mRecipes;

    public RecipeAdapter(List<Recipe> recipes) {
        mRecipes = recipes;
    }

    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_view_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeHolder viewHolder = new RecipeHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeHolder holder, int position) {
        holder.bind(mRecipes.get(position));

    }

    @Override
    public int getItemCount() {
        return (mRecipes == null) ? 0 : mRecipes.size();
    }

    class RecipeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView mRecipeName;

        @BindView(R.id.recipe_servings)
        TextView mRecipeServings;

        void bind(Recipe recipe) {
            mRecipeName.setText(recipe.getName());
            mRecipeServings.setText("Serving: ".concat(String.valueOf(recipe.getServings())));

        }

        public RecipeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
