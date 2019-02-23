package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.shared.AbstractSubgroup
import com.project.mobile_university.data.presentation.Subgroup as SubgroupPresentation
import com.project.mobile_university.data.room.entity.Subgroup as SubgroupSql
import com.project.mobile_university.data.gson.Subgroup as SubgroupGson

object SubgroupMapper {

    fun toDatabase(subgroup: AbstractSubgroup): SubgroupSql {
        return with(subgroup) {
            SubgroupSql(humanValue = humanValue,
                extId = extId,
                name = name)
        }
    }

    fun toPresentation(subgroup: AbstractSubgroup): SubgroupPresentation {
        return with(subgroup) {
            SubgroupPresentation(extId = extId,
                name = name,
                humanValue = humanValue)
        }
    }

    fun toGson(subgroup: AbstractSubgroup): SubgroupGson {
        return with(subgroup) {
            SubgroupGson(humanValue = humanValue,
                name = name,
                extId = extId)
        }
    }

    fun <T : AbstractSubgroup> toDatabase(subgroupList: List<T>) =
        subgroupList.map { toDatabase(it) }

    fun <T : AbstractSubgroup> toPresentation(subgroupList: List<T>) =
        subgroupList.map { toPresentation(it) }

    fun <T : AbstractSubgroup> toGson(subgroupList: List<T>) =
        subgroupList.map { toGson(it) }
}