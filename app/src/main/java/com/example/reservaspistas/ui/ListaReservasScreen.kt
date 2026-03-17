package com.example.reservaspistas.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.reservaspistas.data.Reserva
import com.example.reservaspistas.viewmodel.ReservaViewModel

@Composable
fun ListaReservasScreen(
    viewModel: ReservaViewModel,
    onEditar: (Reserva) -> Unit,
    onVolver: () -> Unit
) {

    val reservas by viewModel.listaReservas.collectAsState()

    var textoBusqueda by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = { onVolver() }
        ) {
            Text("Volver")
        }

        Text("Listado de Reservas", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Row {

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                label = { Text("Buscar reserva...") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.buscar(textoBusqueda) }
            ) {
                Text("🔍")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ENCABEZADO
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text("Cliente", modifier = Modifier.weight(1f))
            Text("Fecha", modifier = Modifier.weight(1f))
            Text("Hora", modifier = Modifier.weight(1f))
            Text("Pista", modifier = Modifier.weight(1f))
            Text("Estado", modifier = Modifier.weight(1f))
            Text("Acciones", modifier = Modifier.weight(1f))
        }

        LazyColumn {

            items(reservas) { reserva ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(reserva.nombre, modifier = Modifier.weight(1f))
                    Text(reserva.fecha, modifier = Modifier.weight(1f))
                    Text(reserva.hora, modifier = Modifier.weight(1f))
                    Text(reserva.pista, modifier = Modifier.weight(1f))

                    // Estado con color
                    val colorEstado =
                        if (reserva.estado == "Activa") Color(0xFF4CAF50)
                        else Color(0xFFF44336)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(colorEstado)
                            .padding(4.dp)
                    ) {
                        Text(reserva.estado, color = Color.White)
                    }

                    Row(modifier = Modifier.weight(1f)) {

                        IconButton(onClick = {
                            onEditar(reserva)
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }

                        IconButton(onClick = {
                            viewModel.eliminarReserva(reserva)
                        }) {
                            Text("🗑")
                        }
                    }
                }
            }
        }
    }
}