package com.example.abhishekpathak.data

import com.example.domain.Comment
import com.example.domain.Post
import com.example.domain.User
import com.example.abhishekpathak.data.database.Post as DomainPost
import com.example.abhishekpathak.data.database.User as DomainUser
import com.example.abhishekpathak.data.database.Comment as DomainComment
import com.example.abhishekpathak.data.server.Comment as ServerComment
import com.example.abhishekpathak.data.server.Post as ServerPost
import com.example.abhishekpathak.data.server.User as ServerUser

fun Post.toRoomPost(): DomainPost {
    return DomainPost(
        postId,
        userId,
        title,
        body,
        wasRead,
        favorite
    )
}

fun DomainPost.toDomainPost(): Post {
    return Post(
        postId,
        userId,
        title,
        body,
        wasRead,
        favorite
    )
}

fun ServerPost.toDomainPost(): Post {
    return Post(
        postId,
        userId,
        title,
        body,
        wasRead,
        favorite
    )
}

fun User.toRoomUser(): DomainUser {
    return DomainUser(
        userId,
        name,
        email,
        phone,
        website
    )
}

fun DomainUser.toDomainUser(): User {
    return User(
        userId,
        name,
        email,
        phone,
        website
    )
}

fun ServerUser.toDomainUser(): User {
    return User(
        userId,
        name,
        email,
        phone,
        website
    )
}


fun Comment.toRoomComment(): DomainComment {
    return DomainComment(
        postId,
        id,
        name,
        email,
        body
    )
}


fun DomainComment.toDomainComment(): Comment {
    return Comment(
        postId,
        id,
        name,
        email,
        body
    )
}


fun ServerComment.toDomainComment(): Comment {
    return Comment(
        postId,
        id,
        name,
        email,
        body
    )
}

