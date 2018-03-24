package com.example.itamarborges.baking.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itamarborges on 12/03/18.
 */

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<Ingredient>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<Step>();
            in.readList(steps, Step.class.getClassLoader());
        } else {
            steps = null;
        }
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
