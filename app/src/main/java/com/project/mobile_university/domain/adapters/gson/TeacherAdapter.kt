package com.project.mobile_university.domain.adapters.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.Teacher
import java.lang.reflect.Type

class TeacherAdapter : JsonDeserializer<Teacher> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Teacher {
        val teacher = json as JsonObject

        val email = teacher.getAsJsonPrimitive("email").asString
        val firstName = teacher.getAsJsonPrimitive("first_name").asString
        val lastName = teacher.getAsJsonPrimitive("last_name").asString
        val cathedraId = teacher.getAsJsonPrimitive("cathedra_id").asLong
        val teacherId = teacher.getAsJsonPrimitive("teacher_id").asLong

        return Teacher(
            email = email,
            teacherId = teacherId,
            cathedraId = cathedraId,
            firstName = firstName,
            lastName = lastName,
            isTeacher = true
        )
    }
}