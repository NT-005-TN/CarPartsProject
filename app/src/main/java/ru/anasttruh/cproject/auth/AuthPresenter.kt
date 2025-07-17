package ru.anasttruh.cproject.auth

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class AuthPresenter(
    private val view: AuthContract.View,
    private val repository: AuthRepository
): AuthContract.Presenter {
    override fun onLoginClicked(email: String, password: String) {
        if (email.isBlank()) {
            view.showEmailError("Введите email")
            return
        }
        if (password.isBlank()) {
            view.showPasswordError("Введите пароль")
            return
        }

        repository.login(email, password, {
            saveLoginData(email, password)
            view.showAuthSuccess()
            view.navigateToMainScreen()
        }, { error ->
            view.showAuthError(error)
        })
    }

    override fun onRegisterClicked(email: String, password: String) {
        repository.register(email, password, {
            view.showAuthSuccess()
            view.navigateToMainScreen()
        }, { error ->
            view.showAuthError(error)
        })
    }

    override fun onGoogleSignInClicked() {
        (view as? AuthActivity)?.launchGoogleSignIn()
    }

    private fun saveLoginData(email: String, password: String) {
        if ((view as? AuthActivity)?.binding?.checkBox?.isChecked == true) {
            val prefs = view.getSharedPreferences("auth_prefs", AppCompatActivity.MODE_PRIVATE)
            prefs.edit {
                putString("email", email)
                    .putString("password", password)
                    .putBoolean("remember", true)
            }
        } else {
            val prefs = (view as AuthActivity).getSharedPreferences("auth_prefs", AppCompatActivity.MODE_PRIVATE)
            prefs.edit {
                clear()
            }
        }
    }

    override fun onGoogleSignInResult(idToken: String?) {
        if (idToken == null) {
            view.showAuthError("Не удалось получить токен Google")
            return
        }

        repository.signInWithGoogle(idToken, {
            view.showAuthSuccess()
            view.navigateToMainScreen()
        }, { error ->
            view.showAuthError(error)
        })
    }

}


