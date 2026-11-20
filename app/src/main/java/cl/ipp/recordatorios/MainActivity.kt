package cl.ipp.recordatorios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF3457A4))) { ReminderScreen() } }
    }
}

@Composable
fun ReminderScreen() {
    var selected by remember { mutableStateOf("Todos") }
    val all = ReminderRepository.reminders
    val visible = when (selected) {
        "Pendientes" -> all.filter { it.status == ReminderStatus.Pending }
        "Alta prioridad" -> all.filter { it.priority == Priority.HIGH }
        else -> all
    }
    Surface(Modifier.fillMaxSize(), color = Color(0xFFF6F7FB)) {
        Column(Modifier.fillMaxSize().padding(20.dp)) {
            Text("Hola, Juan Carlos", fontSize = 15.sp, color = Color(0xFF68738A))
            Text("Mis recordatorios", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF18233B))
            Spacer(Modifier.height(8.dp))
            Text("Organiza tus tareas y mantén el control de tu día.", color = Color(0xFF68738A))
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Todos", "Pendientes", "Alta prioridad").forEach { option ->
                    FilterChip(selected = selected == option, onClick = { selected = option }, label = { Text(option) })
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tareas", fontSize = 19.sp, fontWeight = FontWeight.Bold)
                Text("${visible.size} recordatorios", color = Color(0xFF68738A), fontSize = 13.sp)
            }
            Spacer(Modifier.height(8.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(visible, key = { it.id }) { reminder -> ReminderCard(reminder) }
            }
            Spacer(Modifier.height(8.dp))
            Text("Actividad 1 · Programación Mobile", modifier = Modifier.align(Alignment.CenterHorizontally), color = Color(0xFF8992A5), fontSize = 11.sp)
        }
    }
}

@Composable
fun ReminderCard(item: Reminder) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(18.dp), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(item.title, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color(0xFF202B43))
            item.description?.let { Spacer(Modifier.height(4.dp)); Text(it, color = Color(0xFF68738A), fontSize = 13.sp) }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Tag(item.category.label, Color(0xFFE8EEFA))
                Tag("Prioridad ${item.priority.label}", when(item.priority) {
                    Priority.HIGH -> Color(0xFFFFE3E3)
                    Priority.MEDIUM -> Color(0xFFFFEFCF)
                    Priority.LOW -> Color(0xFFDDF3E4)
                })
                Spacer(Modifier.weight(1f))
                Text(item.status.label, fontSize = 11.sp, color = Color(0xFF52617A))
            }
        }
    }
}

@Composable
fun Tag(label: String, color: Color) {
    Text(label, modifier = Modifier.background(color, RoundedCornerShape(50)).padding(horizontal = 10.dp, vertical = 6.dp), fontSize = 10.sp, color = Color(0xFF34415B))
}
