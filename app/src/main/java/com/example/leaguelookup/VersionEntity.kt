package com.example.leaguelookup

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "versions")
data class VersionEntity(
    @PrimaryKey val id: Int = 1,
    val version: String
)