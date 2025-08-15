package malok.testtask.pokemonapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import malok.testtask.pokemonapp.R

@Composable
fun HeaderSearch(viewModel: PokemonViewModel) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.logo_header),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search") },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                singleLine = true
            )
            IconButton(onClick = {
                viewModel.refreshPokemons()
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "FILTER")
            }
        }
    }
}
