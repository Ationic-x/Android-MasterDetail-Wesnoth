package com.example.u3p2_masterdetail.units;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

// Annotation of Entity (table)
@Entity
// Class model with the values related unit
public class Unit {
    @PrimaryKey(autoGenerate = true)
    private int id;// Primary key id auto generated
    @NonNull
    private String name;
    @NonNull
    private String description;

    private int image;

    private int cost;

    private int hp;

    private int xp;

    private int mp;

    // Constructor with initial values (related NewUnitFragment)
    public Unit(@NotNull String name, @NotNull String description, int image, int cost, int hp, int xp, int mp) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.cost = cost;
        this.hp = hp;
        this.xp = xp;
        this.mp = mp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }
}
