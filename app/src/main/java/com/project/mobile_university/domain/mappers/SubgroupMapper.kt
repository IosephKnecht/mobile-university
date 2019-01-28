package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.Subgroup as SubgroupPresentation
import com.project.mobile_university.data.room.entity.Subgroup as SubgroupSql
import com.project.mobile_university.data.gson.Subgroup as SubgroupGson

object SubgroupMapper {
    fun toDatabase(subgroupGson: SubgroupGson): SubgroupSql {
        return with(subgroupGson) {
            SubgroupSql(humanValue = humanValue,
                name = name)
        }
    }

    fun toDatabase(subgroupGsonList: List<SubgroupGson>): List<SubgroupSql> {
        return subgroupGsonList.map { toDatabase(it) }
    }

    fun toPresentation(subgroupGson: SubgroupGson): SubgroupPresentation {
        return with(subgroupGson) {
            SubgroupPresentation(humanValue = humanValue,
                name = name)
        }
    }

    fun toPresentation(subgroupGsonList: List<SubgroupGson>): List<SubgroupPresentation> {
        return subgroupGsonList.map { toPresentation(it) }
    }
}