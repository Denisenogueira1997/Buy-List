package com.example.buylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buylist.data.Produto
import com.example.buylist.data.ProdutoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProdutoViewModel @Inject constructor(
    private val dao: ProdutoDao
) : ViewModel() {


    val produtos: StateFlow<List<Produto>> =
        dao.listarTodos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val total: StateFlow<Double> =
        produtos.map { lista -> lista.sumOf { it.preco * it.quantidade } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)


    fun apagarProduto(produto: Produto) {
        viewModelScope.launch {
            dao.apagar(produto)
        }
    }

    fun adicionar(nome: String, preco: Double, quantidade: Int) {
        val produto = Produto(nome = nome, preco = preco, quantidade = quantidade)
        viewModelScope.launch {
            dao.insert(produto)
        }
    }

    fun atualizar(produto: Produto) {
        viewModelScope.launch {
            dao.atualizar(produto)
        }
    }
}
