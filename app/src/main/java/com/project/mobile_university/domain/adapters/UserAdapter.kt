package com.project.mobile_university.domain.adapters

import com.google.gson.*
import com.project.mobile_university.data.exceptions.UnknownUserTypeException
import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.gson.User
import java.lang.Exception
import java.lang.reflect.Type

class UserAdapter : JsonDeserializer<User>, JsonSerializer<User> {
    override fun serialize(src: User?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return when (src) {
            is Student -> {
                val userGson = JsonObject()

                createDefaultParams(userGson, src)

                userGson.apply {
                    addProperty("is_student", src.isStudent)
                    addProperty("group_id", src.groupId)
                    addProperty("subgroup_id", src.subgroupId)
                }
            }
            is Teacher -> {
                val userGson = JsonObject()

                createDefaultParams(userGson, src)

                userGson.apply {
                    addProperty("is_teacher", src.isTeacher)
                    addProperty("cathedra_id", src.cathedraId)
                }
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

        val isStudent = user.getAsJsonPrimitiveSafe("is_student", false) { it.asBoolean }
        val isTeacher = user.getAsJsonPrimitiveSafe("is_teacher", false) { it.asBoolean }

        return when {
            isStudent -> {
                val groupId = user.getAsJsonPrimitive("group_id").asLong
                val subgroupId = user.getAsJsonPrimitive("subgroup_id").asLong
                Student(email, firstName, lastName, isStudent, groupId, subgroupId)
            }
            isTeacher -> {
                val cathedraId = user.getAsJsonPrimitive("cathedra_id").asLong
                Teacher(email, firstName, lastName, isTeacher, cathedraId)
            }
            else -> {
                throw UnknownUserTypeException()
            }
        }
    }

    private fun createDefaultParams(userGson: JsonObject, src: User) {
        userGson.addProperty("email", src.email)
        userGson.addProperty("first_name", src.lastName)
        userGson.addProperty("last_name", src.lastName)
    }

    private fun <T> JsonObject.getAsJsonPrimitiveSafe(key: String, defaultValue: T,
                                                      convertBlock: (primitive: JsonPrimitive) -> T): T {
        val primitive = getAsJsonPrimitive(key)
        return if (primitive == null)
            defaultValue
        else try {
            convertBlock(primitive)
        } catch (e: Exception) {
            defaultValue
        }
    }
}