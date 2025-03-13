package cervantes.fedra.practicaautenticacioncervantesfedra

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth = auth

        val inputEmail = findViewById<EditText>(R.id.etrEmail)
        val inputPassword = findViewById<EditText>(R.id.etrPassword)
        val inputConfirmPassword = findViewById<EditText>(R.id.etrConfirmPassword)
        val errorMessage = findViewById<TextView>(R.id.tvrError)
        val btnToLogin = findViewById<Button>(R.id.btnGoLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        errorMessage.visibility = View.INVISIBLE

        btnToLogin.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }

        btnRegister.setOnClickListener { v: View? ->
            handleRegistration(
                inputEmail.text.toString().trim { it <= ' ' },
                inputPassword.text.toString(),
                inputConfirmPassword.text.toString(),
                errorMessage
            )
        }
    }

    private fun handleRegistration(
        email: String,
        password: String,
        confirmPassword: String,
        errorMessage: TextView
    ) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError(errorMessage, "Todos los campos deben estar llenos")
        } else if (password != confirmPassword) {
            showError(errorMessage, "Las contraseñas no coinciden")
        } else {
            errorMessage.visibility = View.INVISIBLE
            registerUser(email, password)
        }
    }

    private fun showError(errorMessage: TextView, message: String) {
        errorMessage.text = message
        errorMessage.visibility = View.VISIBLE
    }

    private fun registerUser(email: String, password: String) {
        Log.d("INFO", "Intentando registrar usuario: $email")
        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    Log.d("INFO", "Registro exitoso")
                    startActivity(
                        Intent(this, MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                } else {
                    Log.w("ERROR", "Error en el registro", task.exception)
                    Toast.makeText(
                        this,
                        "El registro falló. Intenta nuevamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
