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
    RECYCLE, GARBAGE, ORGANIC, E_WASTE, HAZARD;

    companion object {
        fun parseValue(value: String) : RecycleType = when (value) {
            RECYCLE.name.capitalize(Locale.CANADA) -> RECYCLE
            GARBAGE.name.capitalize(Locale.CANADA) -> GARBAGE
            ORGANIC.name.capitalize(Locale.CANADA) -> ORGANIC
            E_WASTE.name.capitalize(Locale.CANADA) -> E_WASTE
            HAZARD.name.capitalize(Locale.CANADA) -> HAZARD
            else -> HAZARD
        }
    }


}
