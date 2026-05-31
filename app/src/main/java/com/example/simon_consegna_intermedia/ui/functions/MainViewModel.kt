import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var partite by mutableStateOf(listOf<PartitaObject>())
        private set

    fun addPartita(p: PartitaObject) {
        partite = partite + p
    }
}
