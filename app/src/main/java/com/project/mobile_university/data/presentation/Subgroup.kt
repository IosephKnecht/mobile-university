package com.project.mobile_university.data.presentation

import com.project.mobile_university.data.shared.AbstractSubgroup

data class Subgroup(override val extId: Long,
                    override val humanValue: Long,
                    override val name: String) : AbstractSubgroup