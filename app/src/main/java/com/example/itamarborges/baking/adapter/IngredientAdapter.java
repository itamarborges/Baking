package com.example.itamarborges.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itamarborges.baking.R;
import com.example.itamarborges.baking.pojo.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by itamarborges on 14/03/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private static final String TAG = IngredientAdapter.class.getSimpleName();

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
        this.notifyDataSetChanged();
    }

    private List<Ingredient> mIngredients;

    public IngredientAdapter(List<Ingredient> Ingredients) {
        mIngredients = Ingredients;
    }

    @Override
    public IngredientAdapter.IngredientHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_ingredient;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        IngredientHolder viewHolder = new IngredientHolder(view, context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientHolder holder, int position) {
        holder.bind(mIngredients.get(position));

    }

    @Override
    public int getItemCount() {
        return (mIngredients == null) ? 0 : mIngredients.size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder {

        Context mContext;

        @BindView(R.id.ingredient)
        TextView mIngredient;

        void bind(final Ingredient ingredient) {
            mIngredient.setText(String.valueOf(ingredient.getQuantity()).concat(" ").concat(ingredient.getMeasure().concat(" ").concat(ingredient.getIngredient())));
        }

        public IngredientHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
        }
    }
}
