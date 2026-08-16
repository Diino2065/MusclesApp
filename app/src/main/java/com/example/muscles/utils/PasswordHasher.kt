package com.example.muscles.utils

import at.favre.lib.crypto.bcrypt.BCrypt

object PasswordHasher {

    /**
     * Hash  BCrypt sa 12 karaktera
     */
    fun hashPassword(password: String): String {
        require(password.isNotBlank()) { "Password cannot be blank" }

        return BCrypt.withDefaults()
            .hashToString(12, password.toCharArray())
    }


    fun verifyPassword(password: String, hash: String): Boolean {
        if (password.isBlank() || hash.isBlank()) return false

        return BCrypt.verifyer()
            .verify(password.toCharArray(), hash.toByteArray(Charsets.UTF_8))
            .verified
    }


    fun isBcryptHash(potentialHash: String): Boolean {
        return potentialHash.startsWith("\$2a\$") ||
            potentialHash.startsWith("\$2b\$") ||
            potentialHash.startsWith("\$2y\$")
    }
}
