package com.ndteam.wasteandroidapp.models

import androidx.compose.ui.text.capitalize
import com.google.firebase.firestore.DocumentSnapshot
import com.ndteam.wasteandroidapp.api.WasteAPIKeys
import java.util.*

data class GarbageItem(
    val icon: String,
    val name: String,
    val wayToRecycler: String,
    val type: RecycleType,
) : GarbageIcon(type) {


    companion object {
        fun convertSnapshot(snapshot: DocumentSnapshot): GarbageItem {
//        val date: Date?
//        date = if (snapshot["date"] == null) {
//            val behavior = ServerTimestampBehavior.ESTIMATE
//            snapshot.getDate("OrderDate", behavior)
//        } else {
//            snapshot.getTimestamp("date")!!.toDate()
//        }

            val item = GarbageItem(
                snapshot.getString(WasteAPIKeys.ICON) ?: WasteAPIKeys.ICON,
                snapshot.getString(WasteAPIKeys.NAME) ?: WasteAPIKeys.NAME,
                snapshot.getString(WasteAPIKeys.WAY_TO_RECYCLER) ?: WasteAPIKeys.WAY_TO_RECYCLER,
                RecycleType.parseValue(snapshot.getString(WasteAPIKeys.TYPE) ?: WasteAPIKeys.TYPE)
            )

            return item
        }
    }

}

enum class RecycleType {
    RECYCLE, GARBAGE, ORGANIC, E_WASTE, HAZARD, YARD;

    companion object {
        fun parseValue(value: String) : RecycleType = when (value) {
            RECYCLE.name.lowercase().capitalize(Locale.CANADA) -> RECYCLE
            GARBAGE.name.lowercase().capitalize(Locale.CANADA) -> GARBAGE
            ORGANIC.name.lowercase().capitalize(Locale.CANADA) -> ORGANIC
            E_WASTE.name.lowercase().capitalize(Locale.CANADA).replace('_','-') -> E_WASTE
            HAZARD.name.lowercase().capitalize(Locale.CANADA) -> HAZARD
            YARD.name.lowercase().capitalize(Locale.CANADA) -> YARD
            else -> HAZARD
        }
    }


}

