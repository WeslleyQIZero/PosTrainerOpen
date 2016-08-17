package com.wiseass.postrainer.model.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Movement is either a Stretch or Exercise. Movements are defined by a name, targeted muscles,
 * potential remedy to postural problems, and a description.
 * Created by Ryan on 21/04/2016.
 */
public class Movement implements Parcelable {
    private String name;
    private String targets;
    private String description;
    private String[] steps;
    private String thumbnailResId;
    private String[] imageResId;

    public Movement(String name, String targets, String description, String[] steps, String thumbnailResId, String[] imageResId) {
        this.name = name;
        this.targets = targets;
        this.description = description;
        this.steps = steps;
        this.thumbnailResId = thumbnailResId;
        this.imageResId = imageResId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public String[] getImageResId() {
        return imageResId;
    }

    public void setImageResId(String[] imageResId) {
        this.imageResId = imageResId;
    }

    public String getThumbnailResId() {
        return thumbnailResId;
    }

    public void setThumbnailResId(String thumbnailResId) {
        this.thumbnailResId = thumbnailResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Movement(Parcel in) {
        name = in.readString();
        targets = in.readString();
        description = in.readString();
        steps = in.createStringArray();
        thumbnailResId = in.readString();
        imageResId = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(targets);
        dest.writeString(description);
        dest.writeStringArray(steps);
        dest.writeString(thumbnailResId);
        dest.writeStringArray(imageResId);
    }


    public static final Creator<Movement> CREATOR = new Creator<Movement>() {
        @Override
        public Movement createFromParcel(Parcel in) {
            return new Movement(in);
        }

        @Override
        public Movement[] newArray(int size) {
            return new Movement[size];
        }
    };


}
