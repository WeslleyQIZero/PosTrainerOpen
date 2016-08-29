package com.wiseass.postrainer.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiseass.postrainer.R;
import com.wiseass.postrainer.model.objects.Movement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ryan on 24/04/2016.
 */
public class MovementListAdapter extends RecyclerView.Adapter<MovementListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Movement> data = Collections.emptyList();
    private OnItemClickListener itemClickListener;
    private Context c;

    /**
     * @param context - Context of Activity which contains contains the fragment which manages
     *                this class
     * @param data    - List of NavListItems which contain a title and icon resource id, to be
     *                passed to bound to ViewHolder objects appropriately.
     */
    public MovementListAdapter(Context context, List<Movement> data) {
        this.c = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void setMovementList(ArrayList<Movement> data) {
       /* this.data.clear();
        this.data.addAll(exerciseList);*/
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_movement_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovementListAdapter.ViewHolder holder, final int position) {
        Movement item = data.get(position);
        holder.name.setText(item.getName());
        holder.targets.setText(item.getTargets());
        holder.description.setText(item.getDescription());
        int id = c.getResources().getIdentifier(item.getThumbnailResId() , "drawable", c.getPackageName());
        holder.thumbnail.setImageResource(id);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView targets;
        TextView name;
        TextView description;
        AppCompatImageView thumbnail;
        Button viewDetails;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.lbl_movement_name);
            targets = (TextView) itemView.findViewById(R.id.lbl_movement_target);
            description = (TextView) itemView.findViewById(R.id.lbl_movement_description);
            thumbnail = (AppCompatImageView) itemView.findViewById(R.id.im_movement_thumb);
            viewDetails = (Button)itemView.findViewById(R.id.btn_movement_detail);

            viewDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onMovementCardClick(data.get(getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onMovementCardClick(Movement selectedMovement);
    }

    public void setOnClickListener(final OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
