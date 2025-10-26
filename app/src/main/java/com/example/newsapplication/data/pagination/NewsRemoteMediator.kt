import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.newsapplication.data.dataSource.local.INewsDao
import com.example.newsapplication.data.dataSource.local.NewsEntity
import com.example.newsapplication.data.dataSource.remote.IApiManager
import com.example.newsapplication.utils.NetworkUtils.isNetworkAvailable
@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val api: IApiManager,
    private val dao: INewsDao,
    private val context: Context
) : RemoteMediator<Int, NewsEntity>() {
    private var currentPage = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND ->  currentPage + 1
        }

        val isConnected = isNetworkAvailable(context)

        return try {
            if (!isConnected) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = api.getNews(
                "us",
                "72f42bc2588c4688a72cf0acc7ee280f",
                page = page,
                pageSize = state.config.pageSize
            )

            val articles = response.articles
            Log.d("Mediator", "Loaded ${articles.size} articles (page=$page)")

            if (loadType == LoadType.REFRESH) dao.clearAll()

            dao.insertAll(articles.map {
                NewsEntity(
                    title = it.title ?: "",
                    author = it.author ?: "",
                    urlToImage = it.urlToImage,
                    description = it.description,
                    publishedAt = it.publishedAt ?: ""
                )
            })
            if (articles.isNotEmpty()) {
                currentPage = page
            }

            MediatorResult.Success(endOfPaginationReached = articles.isEmpty())

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
