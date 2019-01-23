package com.project.mobile_university.domain.adapters

import com.google.gson.*
import com.project.mobile_university.data.exceptions.UnknownUserTypeException
import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.User
import java.lang.reflect.Type

class UserAdapter : JsonDeserializer<User>, JsonSerializer<User> {
    override fun serialize(src: User?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return when (src) {
            is Student -> {
                val userGson = JsonObject()

                userGson.addProperty("email", src.email)
                userGson.addProperty("first_name", src.lastName)
                userGson.addProperty("last_name", src.lastName)
                userGson.addProperty("is_student", src.isStudent)
                userGson.addProperty("group_id", src.groupId)
                userGson.addProperty("subgroup_id", src.subgroupId)

                userGson
            }
            else -> {
                throw UnknownUserTypeException()
            }
        }
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): User {
        val user = json as JsonObject

        val email = user.getAsJsonPrimitive("email").asString
        val firstName = user.getAsJsonPrimitive("first_name").asString
        val lastName = user.getAsJsonPrimitive("last_name").asString

        val isStudent = user.getAsJsonPrimitive("is_student").asBoolean

        return when {
            isStudent -> {
                val groupId = user.getAsJsonPrimitive("group_id").asLong
                val subgroupId = user.getAsJsonPrimitive("subgroup_id").asLong
                Student(email, firstName, lastName, isStudent, groupId, subgroupId)
            }
            else -> {
                throw UnknownUserTypeException()
            }
        }
    }
}