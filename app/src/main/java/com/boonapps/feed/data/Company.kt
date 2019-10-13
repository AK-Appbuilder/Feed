package com.boonapps.feed.data

import androidx.room.ColumnInfo

data class Company(@ColumnInfo(name = "company_name")  val name: String,
                   val catchPhrase: String,
                   val bs: String)