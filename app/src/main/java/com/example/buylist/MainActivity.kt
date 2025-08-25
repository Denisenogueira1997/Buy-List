package com.example.buylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buylist.ui.theme.CustomColor
import com.example.buylist.view.TelaGerenciarProdutos
import com.example.buylist.view.TelaProdutos
import com.example.buylist.viewmodel.ProdutoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CustomColor {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "produtos") {
        composable("produtos") {
            val viewModel: ProdutoViewModel = hiltViewModel()
            TelaProdutos(
                viewModel = viewModel, onGerenciar = { navController.navigate("gerenciar") })
        }
        composable("gerenciar") {
            val viewModel: ProdutoViewModel = hiltViewModel()
            TelaGerenciarProdutos(
                onVoltar = { navController.popBackStack() }, viewModel = viewModel
            )
        }
    }
}
