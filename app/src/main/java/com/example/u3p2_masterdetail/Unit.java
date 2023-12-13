package com.example.u3p2_masterdetail;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Unit {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String description;
    public Unit(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
