import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.networkResponses.ApiResponse
import com.example.myapplication.network.networkResponses.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResponse = MutableStateFlow<ApiResponse<AuthResponse>?>(null)
    val loginResponse: StateFlow<ApiResponse<AuthResponse>?> = _loginResponse.asStateFlow()



    fun login(email: String, password: String) {
        _loginResponse.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiClient.api.login(email, password)
            _loginResponse.value = response
        }
    }
}