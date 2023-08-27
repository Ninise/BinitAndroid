package com.ndteam.wasteandroidapp.utils

import android.app.Activity
import android.content.Context
import com.ndteam.wasteandroidapp.R
import com.ndteam.wasteandroidapp.models.AnswerObject
import com.ndteam.wasteandroidapp.models.GameObject
import com.ndteam.wasteandroidapp.models.QuizObject
import com.ndteam.wasteandroidapp.models.RecycleType
import kotlin.random.Random

object GameUtils {

    const val DND_ITEMS_AMOUNT = 10
    const val QUIZ_AMOUNT = 5

    private const val TOTAL_AMOUNT = "total_amount"
    private const val LAST_INDEX = "last_index"

    private const val LAST_QUESTION_INDEX = "last_question_index"

    private val gameSet = arrayListOf(
        GameObject(
            R.drawable.ic_game_item_credit_card_garb,
            "Credit card",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_cup_2_garb,
            "Ceramic garb",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_shopping_bags_garb,
            "Shopping bags",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_pencil_garb,
            "Pencil",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_pen_garb,
            "Pen",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_clothes_garb,
            "Clothes",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_balloon_garb,
            "Balloon",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_sponge_garb,
            "Sponge",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_reuse_bug_garb,
            "Reuse bag",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_receipt_garb,
            "Receipt",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_label_garb,
            "Label",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_key_,
            "Keys",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_wood_garb,
            "Wood",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_toothbrush_garb,
            "Toothbrush",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_soap_garb,
            "Soap",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_paintbrush_garb,
            "Paintbrush",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_optical_disk_garb,
            "Optical disk",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_bolt_garb,
            "Bolt",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_thread_garb,
            "Thread",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_razor_garb,
            "Razor",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_cigarette_garb,
            "Cigarette",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_styrofoam_box_garb,
            "Styrofoam box",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_styrofoam_box_garb,
            "Styrofoam box",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_cup_2_garb,
            "Cup",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_tube_garb,
            "Tube",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_hair_brush_barg,
            "Hairbrush",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_corks_garb,
            "Corks",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_broken_glass_garb,
            "Broken glass",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_chip_bag_rec,
            "Chip bag",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_facemusk_garb,
            "Facemusk",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_candle_garb,
            "Candle",
            RecycleType.GARBAGE),
        GameObject(
            R.drawable.ic_game_item_egg_org,
            "Egg",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_fish_org,
            "Fish",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_peanuts_org,
            "Peanuts",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_bread_org,
            "Bread",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_banana_org,
            "Banana",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_lemon_org,
            "Lemon",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_kiwi_fruit_org,
            "Kiwi",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_vegetable_org,
            "Vegetable",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_bagel_org,
            "Bagel",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_orange_org,
            "Orange",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_meat_on_bone_org,
            "Meat on bone",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_pizza_2_org,
            "Pizza 2",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_pizza_3_org,
            "Pizza 3",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_coffee_beans_org,
            "Coffee beans",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_pie_2_org,
            "Pie 2",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_pie_3_org,
            "Pie 3",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_bread_2_org,
            "Bread 2",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_sandwich,
            "Sandwich",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_diaper_org,
            "Diaper",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_flower_org,
            "Flower",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_egg_shell_org,
            "Egg shell",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_tea_bug_org,
            "Tea bag",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_coffee_filter_org,
            "Tea bag",
            RecycleType.ORGANIC),
        GameObject(
            R.drawable.ic_game_item_lotion_bottle_rec,
            "Lotion bottle",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_toilet_towel_rec,
            "Toilet towel",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_glass_bottle_2_rec,
            "Glass bottle 2",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_notebook_2_rec,
            "Notebook",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_canned_food_3_rec,
            "Canned food 3",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_can_rec,
            "Can 2",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_cardbord_rec,
            "Cardboard",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_tissue_box_rec,
            "Tissue box",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_pizza_box_rec,
            "Pizza box",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_water_bottle_rec,
            "Water bottle",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_yogurt_rec,
            "Yogurt box",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_takeout_box_rec,
            "Takeout box",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_lotion_bottle_2_rec,
            "Lotion bottle 2",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_plastic_cup_rec,
            "Plastic cp",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_canned_rec,
            "Canned food",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_baby_bottle_rec,
            "Baby bottle",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_package_rec,
            "Package unwaxed",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_toilet_paper_rec,
            "Toilet paper",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_notebook_2_rec,
            "Notebook",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_newspaper_rec,
            "Newspaper",
            RecycleType.RECYCLE),
        GameObject(
            R.drawable.ic_game_item_champain_bottle_rec,
            "Champain",
            RecycleType.RECYCLE),
        )

    private val questionsList = arrayListOf<QuizObject>()

    fun getBatchOfQuestions(activity: Activity) : MutableList<QuizObject> {
        val totalAmount = questionsList.size
        val lastIndex = getData(activity, LAST_QUESTION_INDEX)

        return if (lastIndex + 5 < totalAmount) {
            saveData(activity,lastIndex + 5, LAST_QUESTION_INDEX)
            val list = arrayListOf<QuizObject>()
            list.addAll(questionsList.subList(lastIndex, lastIndex + 5))
            list
        } else {
            saveData(activity,0, LAST_QUESTION_INDEX)

            questionsList.shuffle(Random(10))

            val list = arrayListOf<QuizObject>()
            list.addAll(questionsList.subList(0, 4))
            list
        }
    }

    fun saveQuizQuestions(questions: List<QuizObject>) {
        questionsList.addAll(questions)
    }

    fun getBatchOfItems(activity: Activity) : MutableList<GameObject> {
        // save total number of elements
        // save last item index
        // check if last index + 10 is more than total amount
        // if yes -> start over
        // if not -> get next 10 elements

        val totalAmount = gameSet.size
        val lastIndex = getData(activity, LAST_INDEX)

        return if (lastIndex + 10 < totalAmount) {
            saveData(activity,lastIndex + 10, LAST_INDEX)

            val list = arrayListOf<GameObject>()
            list.addAll(gameSet.subList(lastIndex, lastIndex + 10))
            list
        } else {
            saveData(activity,0, LAST_INDEX)

            gameSet.shuffle(Random(40))

            val list = arrayListOf<GameObject>()
            list.addAll(gameSet.subList(0, 10))
            list

        }
    }

    private fun saveData(activity: Activity, num: Int, name: String) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().putInt(name, num).apply()
    }

    private fun getData(activity: Activity, name: String) : Int {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getInt(name, 0)
    }

    fun getCongratsTextBasedOnScore(score: Int) : String {
        return when(score) {
            1 -> Utils.string(R.string.congrats_1_title)
            2 -> Utils.string(R.string.congrats_2_title)
            3 -> Utils.string(R.string.congrats_3_title)
            4 -> Utils.string(R.string.congrats_4_title)
            5 -> Utils.string(R.string.congrats_5_title)
            else -> Utils.string(R.string.congrats_1_title)
        }
    }

    fun getCongratsSubsTextBasedOnScore(score: Int) : String {
        return when(score) {
            1 -> Utils.string(R.string.congrats_3_sub)
            2 -> Utils.string(R.string.congrats_3_sub)
            3 -> Utils.string(R.string.congrats_3_sub)
            4 -> Utils.string(R.string.congrats_4_sub)
            5 -> Utils.string(R.string.congrats_4_sub)
            else -> Utils.string(R.string.congrats_4_sub)
        }
    }

}