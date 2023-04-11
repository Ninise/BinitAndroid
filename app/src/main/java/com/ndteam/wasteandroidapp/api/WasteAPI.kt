package com.ndteam.wasteandroidapp.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ndteam.wasteandroidapp.models.GarbageItem
import com.ndteam.wasteandroidapp.utils.Utils
import com.ndteam.wasteandroidapp.api.WasteAPIKeys.GARBAGE_ITEMS
import com.ndteam.wasteandroidapp.api.WasteAPIKeys.ICON
import com.ndteam.wasteandroidapp.api.WasteAPIKeys.NAME
import com.ndteam.wasteandroidapp.api.WasteAPIKeys.WAY_TO_RECYCLER
import com.ndteam.wasteandroidapp.api.WasteAPIKeys.TYPE
import kotlinx.coroutines.tasks.await


object WasteAPIKeys {
         const val GARBAGE_ITEMS = "garbage_items"
         const val ICON = "icon"
         const val NAME = "name"
         const val WAY_TO_RECYCLER = "wayToRecycler"
         const val TYPE = "type"
}

object WasteApi {

        /*
             KEYS OF GarbageItem
         */

        private var db: FirebaseFirestore

        init {
            db = Firebase.firestore
        }

        fun addGarbageElement(item: GarbageItem) {
                val garbage = hashMapOf(
                        ICON to item.icon,
                        NAME to item.name,
                        WAY_TO_RECYCLER to item.wayToRecycler,
                        TYPE to item.type.name
                )

                db.collection(GARBAGE_ITEMS)
                        .add(garbage)
                        .addOnSuccessListener { Utils.log("DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Utils.log("Error writing document $e") }

        }

        suspend fun searchGarbageElements(query: String) : ArrayList<GarbageItem> {
            val document = db.collection(GARBAGE_ITEMS)
                            .get()
                            .await()

            val items = ArrayList<GarbageItem>()

            if (document != null) {


                for (doc in document.documents) {

                    val item = GarbageItem.convertSnapshot(doc)

                    if (item.name.lowercase().contains(query.lowercase()) || item.type.name.lowercase().contains(query.lowercase())) {
                        items.add(item)
                    }
                }

            } else {
                Utils.log("No such document")
            }

            return items
        }

}