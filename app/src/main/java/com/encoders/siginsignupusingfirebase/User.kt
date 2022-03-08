package com.encoders.siginsignupusingfirebase


class User(
    val created_at: String?, val username: String, val designation: String
) {
    constructor() : this("", "", "") {

    }
}