package com.example.itamarborges.baking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.itamarborges.baking.R;
import com.example.itamarborges.baking.RecipeDetailActivity;
import com.example.itamarborges.baking.StepFullDescriptionFragment;
import com.example.itamarborges.baking.model.RecipeModel;
import com.example.itamarborges.baking.pojo.Recipe;
import com.example.itamarborges.baking.pojo.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by itamarborges on 14/03/18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private static final String TAG = StepAdapter.class.getSimpleName();

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
        this.notifyDataSetChanged();
    }

    private List<Step> mSteps;
    private String mRecipeName;
    private int mRecipeId;
    private Step currentStep;
    StepFullDescriptionFragment stepFragment;
    private FragmentManager fm;

    private boolean mTwoPane;

    public boolean isTwoPane() {
        return mTwoPane;
    }

    public void setTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public StepAdapter(Recipe recipe) {
        mSteps = recipe.getSteps();
        mRecipeName = recipe.getName();
        mRecipeId = recipe.getId();
    }

    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_step_short_description;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StepHolder viewHolder = new StepHolder(view, context);

        fm = ((FragmentActivity)context).getSupportFragmentManager();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepHolder holder, int position) {
        holder.bind(mSteps.get(position));

    }

    @Override
    public int getItemCount() {
        return (mSteps == null) ? 0 : mSteps.size();
    }

    class StepHolder extends RecyclerView.ViewHolder {

        Context mContext;

        @BindView(R.id.step)
        TextView mTextViewStep;

        @BindView(R.id.step_layout)
        LinearLayout mLayout;


        void bind(final Step step) {
            mTextViewStep.setText(String.valueOf(step.getId()+1).concat(". ").concat(step.getShortDescription()));


            mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mTwoPane) {
                        Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                        intent.putExtra(RecipeDetailActivity.INTENT_KEY_ID, step.getId());
                        intent.putExtra(RecipeDetailActivity.INTENT_KEY_RECIPE_NAME, mRecipeName);
                        intent.putExtra(RecipeDetailActivity.INTENT_KEY_RECIPE_ID, mRecipeId);

                        mContext.startActivity(intent);
                    } else {
                        currentStep = RecipeModel.getStep(mRecipeId, step.getId());
                        stepFragment = (StepFullDescriptionFragment) fm.findFragmentById(R.id.step_fragment);
                        stepFragment.setStep(currentStep);
                    }

                }
            });

        }

        public StepHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
        }
    }
}
