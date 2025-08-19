package com.example.buylist.data


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "produto_db"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    fun provideProdutoDao(db: AppDatabase): ProdutoDao {
        return db.produtoDao()
    }

    private val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
        override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE Produto ADD COLUMN quantidade INTEGER NOT NULL DEFAULT 1"
            )
        }
    }
}
