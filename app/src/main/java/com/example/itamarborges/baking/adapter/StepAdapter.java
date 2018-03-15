package com.example.itamarborges.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itamarborges.baking.R;
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

    public StepAdapter(List<Step> steps) {
        mSteps = steps;
    }

    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item_step_short_description;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StepHolder viewHolder = new StepHolder(view, context);

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
        TextView mStep;

        void bind(final Step step) {
            mStep.setText(String.valueOf(step.getId()+1).concat(". ").concat(step.getShortDescription()));
        }

        public StepHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
        }
    }
}
