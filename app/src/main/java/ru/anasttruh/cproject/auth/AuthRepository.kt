package ru.anasttruh.cproject.auth

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    val error = mapFirebaseToRussian(task.exception?.message)
                    onError(error)
                }
            }
    }

    fun register(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    val error = mapFirebaseToRussian(task.exception?.message)
                    onError(error)
                }
            }
    }

    private fun mapFirebaseToRussian(errorMessage: String?): String {
        return when {
            errorMessage == null -> "Неизвестная ошибка"
            "There is no user record" in errorMessage -> "Пользователь с таким email не найден"
            "The password is invalid" in errorMessage -> "Неверный пароль"
            "email address is badly formatted" in errorMessage -> "Неверный формат email"
            "The email address is already in use" in errorMessage -> "Этот email уже используется"
            "Password should be at least" in errorMessage -> "Пароль должен содержать не менее 6 символов"
            else -> "Ошибка: $errorMessage"
        }
    }

    fun signInWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Ошибка входа через Google: ${task.exception?.localizedMessage ?: "неизвестная ошибка"}")
                }
            }
    }
}