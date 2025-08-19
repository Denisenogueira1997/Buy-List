package com.example.buylist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Insert
    suspend fun insert(produto: Produto)

    @Query("SELECT * FROM produto")
    fun listarTodos(): Flow<List<Produto>>

    @Delete
    suspend fun apagar(produto: Produto)

    @Update
    suspend fun atualizar(produto: Produto)
}

