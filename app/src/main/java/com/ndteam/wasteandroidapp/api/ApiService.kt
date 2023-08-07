package com.ndteam.wasteandroidapp.api

import com.ndteam.wasteandroidapp.models.responses.Article
import com.ndteam.wasteandroidapp.models.responses.Product
import com.ndteam.wasteandroidapp.models.responses.QuickSearch
import com.ndteam.wasteandroidapp.models.responses.Response
import retrofit2.http.GET
import retrofit2.http.Query


object WasteAPIKeys {
         const val GARBAGE_ITEMS = "garbage_items"
         const val ICON = "icon"
         const val NAME = "name"
         const val WAY_TO_RECYCLER = "wayToRecycler"
         const val TYPE = "type"
}

interface ApiService {

    @GET("/articles")
    suspend fun getAllArticles(): List<Article>

    @GET("/products")
    suspend fun searchProducts(@Query("query") query: String): Response<List<Product>>

    @GET("/quick_search")
    suspend fun getQuickSearchSuggestions(): Response<List<QuickSearch>>


    /*
         KEYS OF GarbageItem
     */
//
//        private var db: FirebaseFirestore
//
//        init {
//            db = Firebase.firestore
//        }
//
//        fun addGarbageElement(item: GarbageItem) {
//                val garbage = hashMapOf(
//                        ICON to item.icon,
//                        NAME to item.name,
//                        WAY_TO_RECYCLER to item.wayToRecycler,
//                        TYPE to item.type.name
//                )
//
//                db.collection(GARBAGE_ITEMS)
//                        .add(garbage)
//                        .addOnSuccessListener { Utils.log("DocumentSnapshot successfully written!") }
//                        .addOnFailureListener { e -> Utils.log("Error writing document $e") }
//
//        }
//
//        suspend fun searchGarbageElements(query: String) : ArrayList<GarbageItem> {
//            val document = db.collection(GARBAGE_ITEMS)
//                            .get()
//                            .await()
//
//            val items = ArrayList<GarbageItem>()
//
//            if (document != null) {
//
//
//                for (doc in document.documents) {
//
//                    val item = GarbageItem.convertSnapshot(doc)
//
//                    if (item.name.lowercase().contains(query.lowercase()) || item.type.name.lowercase().contains(query.lowercase())) {
//                        items.add(item)
//                    }
//                }
//
//            } else {
//                Utils.log("No such document")
//            }
//
//            return items
//        }
//
//    suspend fun getArticles() : ArrayList<Article> {
//        return arrayListOf(
//            Article(
//                image = "https://www.sciencenews.org/wp-content/uploads/2021/01/013021_plastics_feat-1440x700.jpg",
//                title = "Reuse. Reduce. Recycle",
//                shortDesc = "How to make lifestyle eco-friendly?",
//                content = "How to make lifestyle eco-friendly?, \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\""),
//            Article(
//                image = "https://www.sciencenews.org/wp-content/uploads/2021/01/013021_plastics_feat-1440x700.jpg",
//                title = "Reuse. Reduce. Recycle",
//                shortDesc = "How to make lifestyle eco-friendly?",
//                content = "How to make lifestyle eco-friendly?, \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\""),
//            Article(
//                image = "https://www.sciencenews.org/wp-content/uploads/2021/01/013021_plastics_feat-1440x700.jpg",
//                title = "Reuse. Reduce. Recycle",
//                shortDesc = "How to make lifestyle eco-friendly?",
//                content = "How to make lifestyle eco-friendly?, \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\", \"How to make lifestyle eco-friendly?\""),
//        )
//    }

}