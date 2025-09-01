package com.example.buylist.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.buylist.data.Produto
import com.example.buylist.viewmodel.ProdutoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaGerenciarProdutos(onVoltar: () -> Unit, viewModel: ProdutoViewModel = hiltViewModel()) {
    val produtos by viewModel.produtos.collectAsState()

    var produtoSelecionado by remember { mutableStateOf<String?>(null) }
    var produtoEditavel by remember { mutableStateOf<Produto?>(null) }

    var nomeEdit by remember { mutableStateOf("") }
    var precoEdit by remember { mutableStateOf("") }
    var quantidadeEdit by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Gerenciar Itens",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth()

            )

        },
        containerColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal))


        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(produtos.sortedBy{ it.nome.lowercase() }) { produto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp, horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = produto.nome, modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        produtoEditavel = produto
                                        nomeEdit = produto.nome
                                        precoEdit = produto.preco.toString()
                                        quantidadeEdit = produto.quantidade.toString()
                                    }, maxLines = 1, overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.width(8.dp))


                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "R$ %.2f".format(produto.preco),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "x${produto.quantidade} = ",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }

                            Spacer(modifier = Modifier.width(4.dp))


                            Text(
                                text = "R$ %.2f".format(produto.preco * produto.quantidade),
                                modifier = Modifier.width(80.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onBackground.copy(0.9f),
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(8.dp))


                            IconButton(onClick = { viewModel.apagarProduto(produto) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Apagar",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = onVoltar, modifier = Modifier.fillMaxWidth()

            ) {
                Text("Voltar")
            }
        }
    }

    if (produtoSelecionado != null) {
        AlertDialog(
            onDismissRequest = { produtoSelecionado = null },
            title = { Text("Nome do Produto") },
            text = { Text(produtoSelecionado ?: "") },
            confirmButton = {
                Button(onClick = { produtoSelecionado = null }) {
                    Text("Fechar")
                }
            })
    }
    if (produtoEditavel != null) {
        AlertDialog(
            onDismissRequest = { produtoEditavel = null },
            title = { Text("Editar Produto") },
            text = {
                Column {
                    OutlinedTextField(
                        value = nomeEdit,
                        onValueChange = { nomeEdit = it },
                        label = { Text("Nome") })
                    OutlinedTextField(
                        value = precoEdit,
                        onValueChange = { precoEdit = it },
                        label = { Text("Preço") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = quantidadeEdit,
                        onValueChange = { quantidadeEdit = it.filter { c -> c.isDigit() } },
                        label = { Text("Quantidade") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    produtoEditavel?.let { prod ->
                        val precoDouble = precoEdit.replace(",", ".").toDoubleOrNull() ?: prod.preco
                        val quantidadeInt = quantidadeEdit.toIntOrNull() ?: prod.quantidade
                        viewModel.atualizar(
                            prod.copy(
                                nome = nomeEdit, preco = precoDouble, quantidade = quantidadeInt
                            )
                        )
                    }
                    produtoEditavel = null
                }) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                Button(onClick = { produtoEditavel = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}