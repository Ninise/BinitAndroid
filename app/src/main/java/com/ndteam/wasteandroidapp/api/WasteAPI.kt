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

        fun searchGarbageElements(query: String, callback: (items: ArrayList<GarbageItem>) -> Unit) {
                db.collection(GARBAGE_ITEMS)
                        .get()
                        .addOnSuccessListener { document ->
                                if (document != null) {

                                    val items = ArrayList<GarbageItem>()

                                    for (doc in document.documents) {
                                        items.add(GarbageItem.Companion.convertSnapshot(doc))
                                    }

                                    callback(items)

                                } else {
                                        Utils.log("No such document")
                                }
                        }
                        .addOnFailureListener { exception ->
                                Utils.log("get failed with $exception")
                        }
        }

}