package com.ndteam.wasteandroidapp.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.ndteam.wasteandroidapp.App
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.utils.Const.DEPOT_TYPE
import com.ndteam.wasteandroidapp.utils.Const.ELECTRONIC_WASTE_TYPE
import com.ndteam.wasteandroidapp.utils.Const.GARBAGE_TYPE
import com.ndteam.wasteandroidapp.utils.Const.HHW_TYPE
import com.ndteam.wasteandroidapp.utils.Const.METAL_ITEMS_TYPE
import com.ndteam.wasteandroidapp.utils.Const.NOT_ACCEPTED_TYPE
import com.ndteam.wasteandroidapp.utils.Const.ORGANIC_TYPE
import com.ndteam.wasteandroidapp.utils.Const.OVERSIZE_TYPE
import com.ndteam.wasteandroidapp.utils.Const.RECYCLE_TYPE
import com.ndteam.wasteandroidapp.utils.Const.YARD_WASTE_TYPE
import kotlin.random.Random

object Utils {

    private const val MAIN_CATEGORY_ANIMATION = "main_category_animation"

    fun saveAnimationState(animated: Boolean) {
        App.context.getSharedPreferences("APP", Context.MODE_PRIVATE).edit().putBoolean(MAIN_CATEGORY_ANIMATION, animated)
    }

    fun isListWasAnimated() : Boolean = App.context.getSharedPreferences("APP", Context.MODE_PRIVATE).getBoolean(MAIN_CATEGORY_ANIMATION, false)


    fun string(res: Int) : String {
        return App.context.getString(res)
    }

    fun log(str: String) {
        Log.d("BEAVER", str)
    }

    fun getRandomFloatInRange(min: Float, max: Float): Float {
        return Random.nextFloat() * (max - min) + min
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email.matches(emailRegex)
    }

    fun getDefaultIconByType(type: String) : Int {
        when (type) {
            GARBAGE_TYPE -> return R.drawable.ic_garbage
            HHW_TYPE -> return R.drawable.ic_hazard
            ORGANIC_TYPE -> return R.drawable.ic_organic
            RECYCLE_TYPE -> return R.drawable.ic_recycle
            OVERSIZE_TYPE -> return R.drawable.ic_garbage // change it
            ELECTRONIC_WASTE_TYPE -> return R.drawable.ic_e_waste
            NOT_ACCEPTED_TYPE -> return R.drawable.ic_garbage // change it
            DEPOT_TYPE -> return R.drawable.ic_garbage // change it
            YARD_WASTE_TYPE -> return R.drawable.ic_garbage // change it
            METAL_ITEMS_TYPE -> return R.drawable.ic_garbage // change it
        }

        return R.drawable.ic_garbage // make a default icon
    }
    fun categoryBinImage(type: String) = when(type) {
        RECYCLE_TYPE -> R.drawable.ic_main_recycle_bin
        GARBAGE_TYPE -> R.drawable.ic_main_garbage_bin
        ORGANIC_TYPE -> R.drawable.ic_main_organic_bin
        ELECTRONIC_WASTE_TYPE -> R.drawable.ic_main_e_waste_bin
        HHW_TYPE -> R.drawable.ic_main_hazar_bing
        YARD_WASTE_TYPE -> R.drawable.ic_main_yard_bin

        else -> {
            R.drawable.ic_main_garbage_bin
        }
    }

    fun getCategoryTitleByType(type: String) : String {
        when (type) {
            GARBAGE_TYPE -> return GARBAGE_TYPE.lowercase()
            HHW_TYPE -> return HHW_TYPE.lowercase()
            ORGANIC_TYPE -> return ORGANIC_TYPE.lowercase()
            RECYCLE_TYPE -> return RECYCLE_TYPE.lowercase()
            OVERSIZE_TYPE -> return OVERSIZE_TYPE.lowercase()
            ELECTRONIC_WASTE_TYPE -> return ELECTRONIC_WASTE_TYPE.lowercase()
            NOT_ACCEPTED_TYPE -> return NOT_ACCEPTED_TYPE.lowercase()
            DEPOT_TYPE -> return DEPOT_TYPE.lowercase()
            YARD_WASTE_TYPE -> return YARD_WASTE_TYPE.lowercase()
            METAL_ITEMS_TYPE -> return METAL_ITEMS_TYPE.lowercase()
        }

        return GARBAGE_TYPE.lowercase()
    }

    fun inviteFriends(): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey, check this app out - https://binit.pro")
            type = "text/plain"
        }
        return Intent.createChooser(sendIntent, null)
    }

    fun copyToClipboard(context: Context, text: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Binit", text)
        clipboardManager.setPrimaryClip(clip)
    }

}