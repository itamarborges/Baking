package com.example.itamarborges.baking.adapter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itamarborges.baking.BakingAppWidget;
import com.example.itamarborges.baking.R;
import com.example.itamarborges.baking.RecipeMasterActivity;
import com.example.itamarborges.baking.pojo.Ingredient;
import com.example.itamarborges.baking.pojo.Recipe;
import com.squareup.picasso.Picasso;

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
        mDone.onDone();
    }

    private List<Recipe> mRecipes;
    private Done mDone;

    public RecipeAdapter(Done mActivity, List<Recipe> recipes) {
        mRecipes = recipes;
        mDone = mActivity;
    }

    public interface Done {
        public void onDone();
    }

    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_view_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeHolder viewHolder = new RecipeHolder(view, context);

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

        Context mContext;

        @BindView(R.id.recipe_name)
        TextView mRecipeName;

        @BindView(R.id.recipe_servings)
        TextView mRecipeServings;

        @BindView(R.id.cv_recipe)
        CardView mCvRecipe;

        @BindView(R.id.img_recipe)
        ImageView mImage;

        void bind(final Recipe recipe) {

            if (recipe.getImage().isEmpty()) {
                mImage.setImageResource(R.drawable.cooking);
            } else {
                Picasso.with(mContext)
                        .load(recipe.getImage())
                        .error(R.drawable.cooking)
                        .placeholder(R.drawable.cooking)
                        .into(mImage);
            }

            mRecipeName.setText(recipe.getName());
            mRecipeServings.setText("Serving: ".concat(String.valueOf(recipe.getServings())));

            mCvRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BakingAppWidget.class));
                    BakingAppWidget.updateFromActivity(mContext, appWidgetManager, appWidgetIds, recipe);


                    Intent intent = new Intent(mContext, RecipeMasterActivity.class);
                    intent.putExtra(RecipeMasterActivity.INTENT_KEY_ID, recipe.getId());

                    mContext.startActivity(intent);
                }
            });




        }

        public RecipeHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
        }
    }
}
