package com.wiseass.postrainer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiseass.postrainer.R;
import com.wiseass.postrainer.ui.widget.NavigationListItem;


import java.util.Collections;
import java.util.List;

/**
 * Adapter class for Globally used Navigation Drawer.
 * Created by Ryan on 18/09/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<NavigationListItem> data = Collections.emptyList();
    private OnItemClickListener mItemClickListener;

    /**
     *
     * @param context - Context of Activity which contains contains the fragment which manages
     *                this class
     * @param data - List of NavListItems which contain a title and icon resource id, to be
     *             passed to bound to ViewHolder objects appropriately.
     */
    public NavigationDrawerAdapter(Context context, List<NavigationListItem> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nav_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder holder, final int position) {
        NavigationListItem item = data.get(position);
        holder.title.setText(item.title);
        holder.icon.setImageResource(item.icon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewGroup container;
        TextView title;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);

            container = (ViewGroup) itemView.findViewById(R.id.nav_list_container);
            title = (TextView) itemView.findViewById(R.id.nav_list_title);
            icon = (ImageView) itemView.findViewById(R.id.nav_list_icon);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
