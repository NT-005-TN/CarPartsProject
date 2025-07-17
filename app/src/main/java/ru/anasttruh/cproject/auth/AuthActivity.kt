package ru.anasttruh.cproject.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import ru.anasttruh.cproject.R
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import ru.anasttruh.cproject.car.CarListActivity
import ru.anasttruh.cproject.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity(), AuthContract.View {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                presenter.onGoogleSignInResult(account?.idToken)
            } catch (e: ApiException) {
                showAuthError("Ошибка входа в Google")
            }
        }

    lateinit var binding: ActivityAuthBinding
    private lateinit var presenter: AuthContract.Presenter
    private var isPasswordVisible = false

    private val prefs by lazy {
        getSharedPreferences("auth_prefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = AuthPresenter(this, AuthRepository()) // ← теперь всё правильно

        binding.authButton.setOnClickListener {
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPassword.text.toString()
            presenter.onLoginClicked(email, password)
        }

        binding.regButton.setOnClickListener {
            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPassword.text.toString()
            presenter.onRegisterClicked(email, password)
        }

        binding.btnGoogleSignIn.setOnClickListener {
            presenter.onGoogleSignInClicked()
        }

        binding.btnTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                binding.editTextPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            binding.editTextPassword.setSelection(binding.editTextPassword.text?.length ?: 0)
        }

        val savedEmail = prefs.getString("email", "")
        val savedPassword = prefs.getString("password", "")
        val remember = prefs.getBoolean("remember", false)

        if (remember) {
            binding.editTextEmailAddress.setText(savedEmail)
            binding.editTextPassword.setText(savedPassword)
            binding.checkBox.isChecked = true
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    override fun showEmailError(message: String) {
        binding.editTextEmailAddress.error = message
    }

    override fun showPasswordError(message: String) {
        binding.editTextPassword.error = message
    }

    override fun showAuthSuccess() {
        Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
    }

    override fun showAuthError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToMainScreen() {
        val intent = Intent(this, CarListActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun launchGoogleSignIn() {
        val intent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(intent)
    }
}
