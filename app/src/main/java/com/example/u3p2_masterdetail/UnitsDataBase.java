package com.example.u3p2_masterdetail;

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

@Database(entities = { Unit.class }, version = 1, exportSchema = false)
public abstract class UnitsDataBase extends RoomDatabase {
    private static volatile UnitsDataBase INSTANCE;


    @Dao
    interface UnitsDao {
        @Query("SELECT * FROM Unit")
        LiveData<List<Unit>> get();

        @Insert
        void insert(Unit unit);
        @Delete
        void delete(Unit unit);
    }
    static UnitsDataBase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (UnitsDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, UnitsDataBase.class, "units.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UnitsDao getUnitsDao();
}