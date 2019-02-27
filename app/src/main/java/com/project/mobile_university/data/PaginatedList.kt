package com.project.mobile_university.data

import com.project.mobile_university.data.gson.BaseServerResponse

data class PaginatedList<T>(val limit: Int,
                            val next: String?,
                            val offset: Int,
                            val previous: String?,
                            val size: Int,
                            val pageNumber: Int,
                            val objects: List<T>?)


fun <T> BaseServerResponse<T>.map() = with(this) {
    PaginatedList(limit = meta.limit,
        next = meta.nextPage,
        offset = meta.offset,
        pageNumber = meta.pageNumber,
        previous = meta.previousPage,
        size = meta.size,
        objects = objectList)
}