package com.wiseass.postrainer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.wiseass.postrainer.R;

/**
 * Created by Ryan on 08/08/2016.
 */
public class FragmentDisclaimer  extends Fragment {

    private CheckBox disclaimerAccepted;
    private Button proceed;

    private FragmentDisclaimerCallback callback;

    public FragmentDisclaimer() {
    }

    public static FragmentDisclaimer newInstance() {
        FragmentDisclaimer fragment = new FragmentDisclaimer();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_disclaimer, container, false);
        disclaimerAccepted = (CheckBox) v.findViewById(R.id.chb_disclaimer_accepted);
        proceed = (Button) v.findViewById(R.id.btn_disclaimer_proceed);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        proceed.setEnabled(false);
        disclaimerAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()){
                    proceed.setEnabled(true);
                    proceed.setTextColor(ContextCompat.getColor(getActivity(), R.color.WHITE));
                } else {
                    proceed.setEnabled(false);
                    proceed.setTextColor(ContextCompat.getColor(getActivity(), R.color.ALPHA_WHITE));
                }
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDisclaimerAccepted();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentDisclaimerCallback) {
            callback = (FragmentDisclaimerCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public interface FragmentDisclaimerCallback {
        //includes a click anywhere except the switch
        void onDisclaimerAccepted();
    }
}
