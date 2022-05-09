package vn.edu.huflit.npl_19dh110839.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {Cart.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    //    public static  AbstractExecutorService databaseWriteExecutor;
    private static AppDatabase INSTANCE;
    public abstract CartDao cartDao();

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(1);

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class , "CartDB")
                    .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}

