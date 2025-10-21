import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.domain.NewsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class NewsViewModel(
     val useCase: NewsUseCase
) : ViewModel() {

    val newsFlow = useCase("us")
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}