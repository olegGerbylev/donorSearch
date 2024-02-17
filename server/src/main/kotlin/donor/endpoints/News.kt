package donor.endpoints

import donor.api.NewsApiService
import donor.api.model.NewsFullData
import donor.api.model.NewsPaginationListData

class News():NewsApiService {
    override fun createNews(newsFullData: NewsFullData): NewsFullData {
        TODO("Not yet implemented")
    }

    override fun getNews(newsId: Long): NewsFullData {
        TODO("Not yet implemented")
    }

    override fun listNews(page: Int?, pageSize: Int?): NewsPaginationListData {
        TODO("Not yet implemented")
    }

    override fun updateNews(newsId: Long, newsFullData: NewsFullData): NewsFullData {
        TODO("Not yet implemented")
    }
}