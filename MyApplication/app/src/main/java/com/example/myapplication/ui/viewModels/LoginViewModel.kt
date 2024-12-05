import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.networkResponses.AuthResponse
import com.example.myapplication.network.networkResponses.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _errorResponse = MutableStateFlow<ErrorResponse?>(null)
    val errorResponse: StateFlow<ErrorResponse?> = _errorResponse.asStateFlow()


    fun login(email: String, password: String) {
        _loginSuccess.value = false
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiClient.api.login(email, password)
            if(response is ErrorResponse){
                _errorResponse.value = response
            }
            if(response is AuthResponse){
                _loginSuccess.value = true
                _errorResponse.value = null
            }
        }
    }
}