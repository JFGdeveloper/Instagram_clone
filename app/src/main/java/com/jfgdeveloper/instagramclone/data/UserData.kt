package com.jfgdeveloper.instagramclone.data

data class UserData(
    var userId: String? = null,
    var name: String? = null,
    var username: String? = null,
    var imageUrl: String? = null,
    var bio: String? = null,
    var following: List<String>? = null
){
    // FIREBASE NECESITA MAPEAR EL DATA
    fun toMap()= mapOf(
            "userId" to userId,
            "name" to name,
            "username" to username,
            "imageUrl" to imageUrl,
            "bio" to bio,
            "following" to following
    )
}
