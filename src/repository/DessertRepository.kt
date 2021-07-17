package com.sarthak.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.sarthak.models.Dessert
import com.sarthak.models.DessertsPage
import com.sarthak.models.PagingInfo
import org.litote.kmongo.getCollection

class DessertRepository(client: MongoClient): RepositoryInterface<Dessert> {
    override lateinit var collection: MongoCollection<Dessert>
    init {
        val database = client.getDatabase("backend")
        collection = database.getCollection<Dessert>("Dessert")
    }

    fun getDessertsPage(page: Int, size: Int): DessertsPage{
        try {
            val skips = page*size
            val response = collection.find().skip(skips).limit(size)
            val results = response.asIterable().map { it }
            val totalDesserts = collection.estimatedDocumentCount()
            val totalPages = if (totalDesserts.toInt()%size==0) totalDesserts/size else (totalDesserts/size) +1
            val next = if ((page+1)*size < totalDesserts.toInt()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(totalDesserts.toInt(), totalPages.toInt(), next, prev)
            return DessertsPage(results, info)
        }catch (t: Throwable){
            throw Exception("Can't get desserts page")
        }
    }
}
