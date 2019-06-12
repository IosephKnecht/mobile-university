package com.project.mobile_university.domain.adapters.gson

import com.google.gson.*
import com.project.mobile_university.data.gson.UserInformation
import java.lang.reflect.Type

class UserInformationAdapter : JsonDeserializer<UserInformation>, JsonSerializer<UserInformation> {

    @Throws(RuntimeException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): UserInformation {
        val userInformation = json as JsonObject

        userInformation.run {
            val id = getAsJsonPrimitive("id").asLong
            val firstName = getAsJsonPrimitive("first_name").asString
            val lastName = getAsJsonPrimitive("last_name").asString
            val email = getAsJsonPrimitive("email").asString
            val userType = getAsJsonPrimitive("user_type").asString

            return when (userType) {
                "teacher" -> {
                    val gender = getAsJsonPrimitive("gender").asInt
                    val cathedraId = getAsJsonPrimitive("cathedra_id").asLong
                    val cathedraName = getAsJsonPrimitive("cathedra_name").asString

                    UserInformation.Teacher(
                        id = id,
                        email = email,
                        gender = gender,
                        userType = userType,
                        firstName = firstName,
                        lastName = lastName,
                        cathedraId = cathedraId,
                        cathedraName = cathedraName
                    )
                }
                "student" -> {
                    val gender = getAsJsonPrimitive("gender").asInt
                    val subgroupId = getAsJsonPrimitive("subgroup_id").asLong
                    val subgroupName = getAsJsonPrimitive("subgroup_name").asString

                    UserInformation.Student(
                        id = id,
                        gender = gender,
                        email = email,
                        userType = userType,
                        firstName = firstName,
                        lastName = lastName,
                        subgroupId = subgroupId,
                        subgroupName = subgroupName
                    )
                }
                "undefined" -> {
                    UserInformation.Undefined(
                        id = id,
                        firstName = firstName,
                        lastName = lastName,
                        userType = userType,
                        email = email
                    )
                }
                else -> {
                    throw RuntimeException("user type could not support")
                }
            }
        }
    }

    override fun serialize(src: UserInformation, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonUserInformation = JsonObject()

        return jsonUserInformation.apply {
            addProperty("id", src.id)
            addProperty("first_name", src.firstName)
            addProperty("last_name", src.lastName)
            addProperty("email", src.email)
            addProperty("user_type", src.userType)

            when (src) {
                is UserInformation.Student -> {
                    addProperty("subgroup_id", src.subgroupId)
                    addProperty("subgroup_name", src.subgroupName)
                    addProperty("gender", src.gender)
                }
                is UserInformation.Teacher -> {
                    addProperty("cathedra_id", src.cathedraId)
                    addProperty("cathedra_name", src.cathedraName)
                    addProperty("gender", src.gender)
                }
            }
        }
    }
}