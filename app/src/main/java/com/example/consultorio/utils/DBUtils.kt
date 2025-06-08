package com.example.consultorio.utils

import com.google.firebase.auth.FirebaseAuth

object DBUtils {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
}