package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.Subgroup as SubgroupPresentation
import com.project.mobile_university.data.room.entity.Subgroup as SubgroupSql
import com.project.mobile_university.data.gson.Subgroup as SubgroupGson

object SubgroupMapper {

    fun toDatabase(subgroup: SubgroupGson): SubgroupSql {
        return with(subgroup) {
            SubgroupSql(
                humanValue = humanValue,
                extId = extId,
                name = name
            )
        }
    }

    fun toDatabase(subgroup: SubgroupPresentation): SubgroupSql {
        return with(subgroup) {
            SubgroupSql(
                humanValue = humanValue,
                name = name,
                extId = extId
            )
        }
    }

    fun toPresentation(subgroup: SubgroupSql): SubgroupPresentation {
        return with(subgroup) {
            SubgroupPresentation(
                extId = extId,
                name = name,
                humanValue = humanValue
            )
        }
    }

    fun toPresentation(subgroup: SubgroupGson): SubgroupPresentation {
        return with(subgroup) {
            SubgroupPresentation(
                extId = extId,
                humanValue = humanValue,
                name = name
            )
        }
    }

    fun gsonToSql(list: List<SubgroupGson>) = list.map { SubgroupMapper.toDatabase(it) }

    fun presentationToSql(list: List<SubgroupPresentation>) = list.map { SubgroupMapper.toDatabase(it) }

    fun sqlToPresetation(list: List<SubgroupSql>) = list.map { SubgroupMapper.toPresentation(it) }

    fun gsonToPresentation(list: List<SubgroupGson>) = list.map { SubgroupMapper.toPresentation(it) }
}