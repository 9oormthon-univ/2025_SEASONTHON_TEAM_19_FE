package com.example.synergy.util

import com.example.synergy.ui.home.model.Category
import com.example.synergy.ui.home.model.Mentor

object DummyData {
    val dummyMentors = listOf(
        Mentor(
            id = 1,
            name = "aiMentor",
            email = "ai@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "HOBBY", category = "취미"),
                Category(code = "AI", category = "AI")
            ),
            introduction = null
        ),
        Mentor(
            id = 2,
            name = "digitalMentor",
            email = "digital@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "DIGITAL_UTIL", category = "디지털활용")
            ),
            introduction = null
        ),
        Mentor(
            id = 3,
            name = "hobbyMentor",
            email = "hobby@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "HOBBY", category = "취미")
            ),
            introduction = null
        ),
        Mentor(
            id = 4,
            name = "jobMentor",
            email = "job@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "JOB_WORK", category = "취업 & 일")
            ),
            introduction = null
        ),
        Mentor(
            id = 5,
            name = "careMentor",
            email = "care@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "CARE", category = "돌봄")
            ),
            introduction = null
        ),
        Mentor(
            id = 6,
            name = "exerciseMentor",
            email = "exercise@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "EXERCISE", category = "운동")
            ),
            introduction = null
        ),
        Mentor(
            id = 7,
            name = "lifeMentor",
            email = "life@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "LIFE", category = "생활")
            ),
            introduction = null
        ),
        Mentor(
            id = 8,
            name = "mindMentor",
            email = "mind@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "MIND", category = "마음")
            ),
            introduction = null
        ),
        Mentor(
            id = 9,
            name = "normalUser",
            email = "normal@example.com",
            isMentor = true,
            categories = listOf(
                Category(code = "HOBBY", category = "취미"),
                Category(code = "AI", category = "AI")
            ),
            introduction = null
        )
    )
}