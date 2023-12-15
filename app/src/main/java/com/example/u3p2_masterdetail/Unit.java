package com.example.u3p2_masterdetail;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Annotation of Entity (table)
@Entity
// Class model with the values related unit
public class Unit {
    @PrimaryKey(autoGenerate = true)
    int id;// Primary key id auto generated
    String name;
    String description;
    int image;
    int cost;
    int hp;
    int xp;
    int mp;

    // Constructor with initial values (related NewUnitFragment)
    public Unit(String name, String description, int image, int cost, int hp, int xp, int mp) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.cost = cost;
        this.hp = hp;
        this.xp = xp;
        this.mp = mp;
    }
}
