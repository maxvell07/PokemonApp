package malok.testtask.pokemonapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onApply: (sortBy: String, selectedTypes: Set<String>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Sort by", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        val sortOptions = listOf("NUMBER", "NAME", "HP", "ATTACK", "DEFENSE")
        var selectedSort by remember { mutableStateOf("NUMBER") }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            sortOptions.forEach { option ->
                FilterButton(
                    text = option,
                    selected = selectedSort == option
                ) { selectedSort = option }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Filter by Type", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        val typeOptions = listOf(
            "Normal","Fire","Water","Electric","Grass",
            "Ice","Fighting","Poison","Ground","Flying",
            "Psychic","Bug","Rock","Ghost","Dragon",
            "Dark","Steel","Fairy"
        )
        var selectedTypes by remember { mutableStateOf(setOf<String>()) }

        // Кнопки по строкам (можно улучшить)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            typeOptions.chunked(5).forEach { rowItems ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    rowItems.forEach { type ->
                        FilterButton(
                            text = type,
                            selected = selectedTypes.contains(type),
                            color = getTypeColor(type)
                        ) {
                            selectedTypes = if (selectedTypes.contains(type)) {
                                selectedTypes - type
                            } else {
                                selectedTypes + type
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onApply(selectedSort, selectedTypes)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Apply Filters")
        }
    }
}

@Composable
fun FilterButton(text: String, selected: Boolean, color: Color = Color.DarkGray, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) color else Color.Transparent,
            contentColor = if (selected) Color.White else Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        border = if (!selected) BorderStroke(1.dp, Color.White) else null,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(text)
    }
}

// Пример простой функции для типа
fun getTypeColor(type: String): Color = when(type) {
    "Fire" -> Color.Red
    "Steel", "Ghost","Rock", "Ground" ->Color.DarkGray
    "Ice","Water" -> Color.Blue
    "Electric" -> Color.Yellow
    "Dragon","Fighting" -> Color(0xFFD32F2F)
    "Poison", "Bug" -> Color(0xFFCDDC39)
    "Fairy" -> Color(0xFFF48FB1)
    else -> Color.DarkGray
}

@Preview
@Composable
fun previewbotomsheet(){
//    FilterBottomSheet {  }
}