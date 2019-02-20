package com.project.mobile_university.domain.mappers

import com.project.mobile_university.data.presentation.Subgroup as SubgroupPresentation
import com.project.mobile_university.data.room.entity.Subgroup as SubgroupSql
import com.project.mobile_university.data.gson.Subgroup as SubgroupGson

object SubgroupMapper {
    fun toDatabase(subgroupGson: SubgroupGson): SubgroupSql {
        return with(subgroupGson) {
            SubgroupSql(humanValue = humanValue,
                name = name,
                extId = this.id)
        }
    }

    fun toDatabase(subgroupGsonList: List<SubgroupGson>): List<SubgroupSql> {
        return subgroupGsonList.map { toDatabase(it) }
    }

    fun toPresentation(subgroupGson: SubgroupGson): SubgroupPresentation {
        return with(subgroupGson) {
            SubgroupPresentation(humanValue = humanValue,
                name = name,
                extId = id)
        }
    }

    fun toPresentation(subgroupGsonList: List<SubgroupGson>): List<SubgroupPresentation> {
        return subgroupGsonList.map { toPresentation(it) }
    }

    fun sqlToPresentation(subgroupSql: SubgroupSql): SubgroupPresentation {
        return with(subgroupSql) {
            SubgroupPresentation(humanValue = this.humanValue,
                name = this.name,
                extId = extId)
        }
    }

    fun sqlToPresentation(subgroupSqlList: List<SubgroupSql>): List<SubgroupPresentation> {
        return subgroupSqlList.map { sqlToPresentation(it) }
    }

    fun toGson(subgroup: SubgroupPresentation): SubgroupGson {
        return with(subgroup) {
            SubgroupGson(id = extId,
                humanValue = humanValue,
                name = name)
        }
    }

    fun toGson(subgroups: List<SubgroupPresentation>): List<SubgroupGson> {
        return subgroups.map { toGson(it) }
    }
}