package com.example.muscles.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies

object PasswordHasher {
    
    /**
     * Hash a password using BCrypt with a cost of 12
     * @param password The plaintext password to hash
     * @return The hashed password
     */
    fun hashPassword(password: String): String {
        return BCrypt.withDefaults()
            .hashToString(12, password.toCharArray())
    }
    
    /**
     * Verify a password against its hash
     * @param password The plaintext password to verify
     * @param hash The hashed password to check against
     * @return True if password matches hash, false otherwise
     */
    fun verifyPassword(password: String, hash: String): Boolean {
        return try {
            BCrypt.verifyer()
                .verify(password.toCharArray(), hash.toByteArray())
                .verified
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Check if a password hash is from old plaintext storage (for migration)
     * @param potential_hash String that might be a hash
     * @return True if it appears to be a bcrypt hash
     */
    fun isBcryptHash(potential_hash: String): Boolean {
        return potential_hash.startsWith("\$2a\$") ||
               potential_hash.startsWith("\$2b\$") ||
               potential_hash.startsWith("\$2y\$")
    }
}
