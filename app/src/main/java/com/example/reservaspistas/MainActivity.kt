package com.example.reservaspistas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reservaspistas.data.AppDatabase
import com.example.reservaspistas.data.Reserva
import com.example.reservaspistas.repository.ReservaRepository
import com.example.reservaspistas.ui.ListaReservasScreen
import com.example.reservaspistas.ui.ReservaScreen
import com.example.reservaspistas.viewmodel.ReservaViewModel
import com.example.reservaspistas.viewmodel.ReservaViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.Companion.getDatabase(applicationContext)
        val repository = ReservaRepository(database.reservaDao())
        val viewModel = ReservaViewModel(repository)

        setContent {

            val context = this

            val database = AppDatabase.getDatabase(context)
            val repository = ReservaRepository(database.reservaDao())
            val factory = ReservaViewModelFactory(repository)

            val viewModel: ReservaViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                factory = factory
            )

            var pantalla by remember { mutableStateOf("form") }
            var reservaEditar by remember { mutableStateOf<Reserva?>(null) }

            when (pantalla) {

                "form" -> ReservaScreen(
                    viewModel = viewModel,
                    reservaEditar = reservaEditar,
                    onIrALista = {
                        pantalla = "lista"
                    }
                )

                "lista" -> ListaReservasScreen(
                    viewModel = viewModel,
                    onEditar = {
                        reservaEditar = it
                        pantalla = "form"
                    },
                    onVolver = {
                        reservaEditar = null
                        pantalla = "form"
                    }
                )
            }
        }
    }
}