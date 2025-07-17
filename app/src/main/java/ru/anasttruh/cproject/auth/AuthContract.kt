package ru.anasttruh.cproject.auth

interface AuthContract {

    interface View {
        fun showEmailError(message: String)
        fun showPasswordError(message: String)
        fun showAuthSuccess()
        fun showAuthError(message: String)
        fun navigateToMainScreen()
        fun launchGoogleSignIn()
    }

    interface Presenter {
        fun onLoginClicked(email: String, password: String)
        fun onRegisterClicked(email: String, password: String)
        fun onGoogleSignInClicked()
        fun onGoogleSignInResult(idToken: String?)
    }
}