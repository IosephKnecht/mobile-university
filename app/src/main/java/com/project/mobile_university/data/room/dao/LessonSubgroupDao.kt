package com.project.mobile_university.data.room.dao

import androidx.room.*
import com.project.mobile_university.data.room.entity.LessonSubgroup
import com.project.mobile_university.data.room.entity.Subgroup
import io.reactivex.Observable

@Dao
interface LessonSubgroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg lessonSubgroup: LessonSubgroup)

    @Delete
    fun delete(vararg lessonSubgroup: LessonSubgroup)

    @Query("""Select subgroup.id, subgroup.humanValue, subgroup.name, subgroup.ext_id from subgroup
        inner join lessonsubgroup on subgroup.id = lessonsubgroup.subgroup_id
        where lessonsubgroup.lesson_id = :lessonId""")
    fun getSubgroupIdsByLessonId(lessonId: Long): List<Subgroup>
}