package com.wiseass.postrainer.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.wiseass.postrainer.R;

/**
 * Created by Ryan on 11/08/2016.
 */
public class FragmentMovementDetailText extends Fragment {
    private static final String MOVEMENT_DESCRIPTION = "MOVEMENT_DESCRIPTION";
    private static final String MOVEMENT_STEPS = "MOVEMENT_STEPS";

    private String movementDescrpition;
    private String[] movementSteps;
    private AppCompatButton tabDescription, tabSteps;
    private TextSwitcher switcher;
    private View divLeft, divRight;
    private Animation inLeft, inRight, outLeft, outRight;

    public static FragmentMovementDetailText newInstance(String movementDescription, String[] movementSteps) {
        FragmentMovementDetailText fragment = new FragmentMovementDetailText();
        Bundle args = new Bundle();
        args.putString(MOVEMENT_DESCRIPTION, movementDescription);
        args.putStringArray(MOVEMENT_STEPS, movementSteps);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.movementDescrpition = getArguments().getString(MOVEMENT_DESCRIPTION);
            this.movementSteps = getArguments().getStringArray(MOVEMENT_STEPS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movement_detail_text, container, false);

        tabDescription = (AppCompatButton) v.findViewById(R.id.imb_fragment_detail_description);
        tabDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDescription();
            }
        });

        tabSteps = (AppCompatButton) v.findViewById(R.id.imb_fragment_detail_steps);
        tabSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSteps();
            }
        });


        divLeft = v.findViewById(R.id.div_fragment_detail_horizontal_left);
        divRight = v.findViewById(R.id.div_fragment_detail_horizontal_right);

        switcher = (TextSwitcher) v.findViewById(R.id.swi_fragment_detail);


        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        inLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
        inRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);

        outRight = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        outLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                TextView tv = new TextView(getActivity());
                tv.setGravity(Gravity.TOP);
                tv.setTextSize(16);
                tv.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                tv.setTextColor(Color.WHITE);
                return tv;
            }
        });


        showDescription();

        super.onActivityCreated(savedInstanceState);
    }

    public void showDescription() {

        switcher.setInAnimation(inLeft);
        switcher.setOutAnimation(outRight);


        switcher.setText(movementDescrpition);
        divRight.setVisibility(View.VISIBLE);
        divLeft.setVisibility(View.INVISIBLE);
        tabDescription.setAlpha(1.0f);
        tabSteps.setAlpha(.66f);
    }

    public void showSteps() {
        switcher.setInAnimation(inRight);
        switcher.setOutAnimation(outLeft);

        switcher.setText(buildSteps());
        divLeft.setVisibility(View.VISIBLE);
        divRight.setVisibility(View.INVISIBLE);
        tabSteps.setAlpha(1.0f);
        tabDescription.setAlpha(.66f);
    }

    private String buildSteps(){
        StringBuilder builder = new StringBuilder();

        for (String string : movementSteps) {
            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(string);
        }
        return builder.toString();
    }
}