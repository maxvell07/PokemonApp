package malok.testtask.pokemonapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import malok.testtask.pokemonapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSearch() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }
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
            var text by remember { mutableStateOf("") }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.White),
                    cursorBrush = SolidColor(Color.White),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(text = "Search", color = Color.White)
                        }
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (showSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    FilterBottomSheet(
                        onApply = {sortBy, types ->
//  todo                          viewModel.applyFilters(sortBy, types)
                            showSheet = false
                        }
                    )
                }
            }
                    IconButton(onClick = {
                        showSheet = true
                    }) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter_icon),
                                contentDescription = "FILTER",
                                modifier = Modifier.fillMaxSize()
                            )

                    }

        }
    }
}
