import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    var loginSuccess = mutableStateOf(false)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.api.login(email, password)
                if(response){
                    loginSuccess.value = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loginSuccess.value = false
                }
            }
        }
    }
}