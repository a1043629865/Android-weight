package com.example.practiceproject

data class Response(
    val reason: String,
    val result: Result,
    val error_code: Int
)


data class Result(
    val bmi: Double,
    val normalWeight: String,
    val levelMsg: String,
    val idealWeight:Double
)

data class NewsResponse(
    val reason: String,
    val result: NewsResult,
    val error_code: Int
)

data class NewsResult(
    val stat: String,
    val data: List<News>
)

data class News(
    val title: String,
    val date: String,
    val author_name: String,
    val thumbnail_pic_s: String,
    val url: String
)

data class WeightData(
    val weight: Double,
    val time: String,
)