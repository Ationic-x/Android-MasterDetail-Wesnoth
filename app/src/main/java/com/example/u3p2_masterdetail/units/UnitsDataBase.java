package com.example.u3p2_masterdetail.units;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.RoomDatabase;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Room;

import java.util.List;

// Annotation related database
@Database(entities = { Unit.class }, version = 2, exportSchema = false)
// Class singleton that works as a DataBase (SQLite)
public abstract class UnitsDataBase extends RoomDatabase {
    private static volatile UnitsDataBase INSTANCE; // Volatile object of itself

    // Dao all methods related to work with the Database
    // Insert, Delete, Custom Query...
    @Dao
    interface UnitsDao {
        @Query("SELECT * FROM Unit")
        LiveData<List<Unit>> get();

        @Insert
        void insert(Unit unit);
        @Update
        void update(Unit unit);
        @Delete
        void delete(Unit unit);
    }

    // Constructor Singleton DataBase
    static UnitsDataBase getInstance(final Context context) {
        // If there is no DataBase
        if (INSTANCE == null) {
            // Synchronize with DataBase
            synchronized (UnitsDataBase.class) {
                // If still no Database
                if (INSTANCE == null) {
                    // Create a new one
                    INSTANCE = Room.databaseBuilder(context, UnitsDataBase.class, "units.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        // Return DataBase
        return INSTANCE;
    }

    // Method get UnitsDao
    abstract UnitsDao getUnitsDao();
}