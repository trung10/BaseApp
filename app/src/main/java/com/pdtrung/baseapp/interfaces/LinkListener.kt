package com.pdtrung.baseapp.interfaces

interface LinkListener {
    fun onViewTag(tag: String)
    fun onViewAccount(id: String)
    fun onViewUrl(url: String)
}