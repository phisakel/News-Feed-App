package gr.phisakel.newsfeed.data.storage

import com.vicpin.krealmextensions.*
import gr.phisakel.newsfeed.data.storage.entity.SourceDB

class NewsSourcesStorage {

    fun queryAll(): List<SourceDB> = SourceDB().queryAll()

    fun queryEnabled(): List<SourceDB> =
            SourceDB().query { query -> query.equalTo("isEnabled", true) }

    fun queryCategory(category: String): List<SourceDB> =
            SourceDB().query { query -> query.equalTo("category", category.toLowerCase()) }

    fun findById(id: String): SourceDB? =
            SourceDB().queryFirst { query -> query.equalTo("id", id) }

    fun save(items: List<SourceDB>) {
        items.saveAll()
    }

    fun deleteAll() {
        SourceDB().deleteAll()
    }
}
