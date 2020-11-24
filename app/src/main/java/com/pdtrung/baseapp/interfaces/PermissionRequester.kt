package com.pdtrung.baseapp.interfaces

interface PermissionRequester {
    fun onRequestPermissionsResult(permissions: List<String>, grantResults: List<Int>)
}