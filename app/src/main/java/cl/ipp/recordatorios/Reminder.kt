package cl.ipp.recordatorios

data class Reminder(
    val id: Int,
    val title: String,
    val description: String? = null,
    val category: ReminderCategory,
    val priority: Priority,
    val status: ReminderStatus,
    val location: String? = null
)

enum class ReminderCategory(val label: String) {
    PERSONAL("Personal"), STUDY("Estudio"), WORK("Trabajo"), HEALTH("Salud")
}

enum class Priority(val label: String) {
    LOW("Baja"), MEDIUM("Media"), HIGH("Alta")
}

sealed class ReminderStatus(val label: String) {
    data object Pending : ReminderStatus("Pendiente")
    data object InProgress : ReminderStatus("En progreso")
    data object Completed : ReminderStatus("Completado")
}

object ReminderRepository {
    val reminders: List<Reminder> = listOf(
        Reminder(1, "Entregar actividad de Kotlin", "Revisar el proyecto y subir el PDF.", ReminderCategory.STUDY, Priority.HIGH, ReminderStatus.Pending),
        Reminder(2, "Comprar alimentos", "Preparar la lista para la semana.", ReminderCategory.PERSONAL, Priority.MEDIUM, ReminderStatus.InProgress),
        Reminder(3, "Repasar Jetpack Compose", "Practicar componentes y estados.", ReminderCategory.STUDY, Priority.HIGH, ReminderStatus.Pending),
        Reminder(4, "Caminar por la tarde", category = ReminderCategory.HEALTH, priority = Priority.LOW, status = ReminderStatus.Completed),
        Reminder(5, "Revisar correo", category = ReminderCategory.WORK, priority = Priority.MEDIUM, status = ReminderStatus.Pending)
    )

    // Set evita categorías duplicadas.
    val categories: Set<ReminderCategory> = reminders.map { it.category }.toSet()

    // Map relaciona cada categoría con un color representado por un valor ARGB.
    val categoryColors: Map<ReminderCategory, Long> = mapOf(
        ReminderCategory.PERSONAL to 0xFFE8DEF8,
        ReminderCategory.STUDY to 0xFFDCEBFF,
        ReminderCategory.WORK to 0xFFFFE7CC,
        ReminderCategory.HEALTH to 0xFFDDF3E4
    )

    fun byStatus(status: ReminderStatus): List<Reminder> =
        reminders.filter { it.status::class == status::class }

    fun byPriority(priority: Priority): List<Reminder> =
        reminders.filter { it.priority == priority }
}
