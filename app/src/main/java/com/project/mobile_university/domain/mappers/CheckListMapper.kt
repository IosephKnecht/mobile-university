package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.gson.CheckListRecord as CheckListRecordGson
import com.project.mobile_university.data.presentation.CheckListRecord as CheckListRecordPresentation

object CheckListMapper {
    fun toPresentation(checkListRecord: CheckListRecordGson): CheckListRecordPresentation {
        return with(checkListRecord) {
            CheckListRecordPresentation(
                id = id,
                student = UserMapper.toPresentation(student),
                status = CheckListStatusMapper.toPresentation(status)!!
            )
        }
    }

    fun toGson(checkListRecord: CheckListRecordPresentation): CheckListRecordGson {
        return with(checkListRecord) {
            CheckListRecordGson(
                id = id,
                student = UserMapper.toGson(student),
                status = CheckListStatusMapper.toValue(status)
            )
        }
    }

    fun gsonToPresentation(list: List<CheckListRecordGson>) = list.map { toPresentation(it) }

    fun presentationToGson(list: List<CheckListRecordPresentation>) = list.map { toGson(it) }
}