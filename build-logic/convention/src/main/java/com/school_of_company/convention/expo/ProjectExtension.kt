package com.school_of_company.convention.expo

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs // Use libs Efficient
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")