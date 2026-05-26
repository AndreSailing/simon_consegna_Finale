import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var partite by mutableStateOf(listOf<String>())
        private set

    fun addPartita(p: String) {
        partite = partite + p
    }
}
