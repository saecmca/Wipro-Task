package com.example.task.model

data class EntryBO(
    var entries:List<EntryItem>

)
data class EntryItem(
    var API:String,
    var Description:String,
    var Category:String,
    var Link:String,

)

