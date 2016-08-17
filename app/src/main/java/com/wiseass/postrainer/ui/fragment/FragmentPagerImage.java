package com.wiseass.postrainer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

/**
 * Created by Ryan on 11/08/2016.
 */
public class FragmentPagerImage extends Fragment {
    private static final String IMAGE_RESOURCE = "IMAGE_RESOURCE";

    private static final int IMAGEVIEW_PADDING = 2;
    private int imageResource;

    public static FragmentPagerImage newInstance(int imageResource) {
        FragmentPagerImage fragment = new FragmentPagerImage();
        Bundle args = new Bundle();
        args.putInt(IMAGE_RESOURCE, imageResource);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.imageResource = getArguments().getInt(IMAGE_RESOURCE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView image = new ImageView(getActivity());
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        image.setPadding(IMAGEVIEW_PADDING,IMAGEVIEW_PADDING,IMAGEVIEW_PADDING,IMAGEVIEW_PADDING);
        //image.setImageResource(imageResource);

        FrameLayout layout = new FrameLayout(getActivity());
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        layout.addView(image);

        Picasso.with(getActivity())
                .load(imageResource)
                .fit()
                .into(image);

        return layout;
    }
}
