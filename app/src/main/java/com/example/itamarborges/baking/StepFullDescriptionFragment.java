package com.example.itamarborges.baking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itamarborges.baking.pojo.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepFullDescriptionFragment extends Fragment {

    private Step mStep;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.step_description)
    TextView stepDescription;

    @BindView(R.id.step_short_description)
    TextView stepShortDescription;

    public StepFullDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_full_description, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public Step getStep() {
        return mStep;
    }

    public void setStep(Step mStep) {
        this.mStep = mStep;
        fillInformation();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void fillInformation() {

        stepShortDescription.setText(mStep.getShortDescription());
        stepDescription.setText(mStep.getDescription());

    }
}
