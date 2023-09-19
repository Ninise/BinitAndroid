package com.whalescale.binit.models

data class QuizObject(
    val question: String,
    val answers: List<AnswerObject>
)

data class AnswerObject(
    val answer: String,
    val explanation: String,
    val isCorrect: Boolean
)