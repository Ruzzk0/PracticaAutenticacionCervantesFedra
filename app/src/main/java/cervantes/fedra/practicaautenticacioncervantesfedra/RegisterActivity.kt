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

        val email = findViewById<EditText>(R.id.etrEmail)
        val password = findViewById<EditText>(R.id.etrPassword)
        val confirmPassword = findViewById<EditText>(R.id.etrConfirmPassword)
        val errorTv = findViewById<TextView>(R.id.tvrError)
        val goLogin = findViewById<Button>(R.id.btnGoLogin)
        val button = findViewById<Button>(R.id.btnRegister)
        errorTv.visibility = View.INVISIBLE

        goLogin.setOnClickListener { v: View? ->
            val intent = Intent(
                this,
                LoginActivity::class.java
            )
            startActivity(intent)
        }

        button.setOnClickListener { v: View? ->
            if (email.text.toString().isEmpty() ||
                password.text.toString().isEmpty() ||
                confirmPassword.text.toString().isEmpty()
            ) {
                errorTv.text = "Todos los campos deben de ser llenados"
                errorTv.visibility = View.VISIBLE
            } else if (password.text.toString() != confirmPassword.text.toString()) {
                errorTv.text = "Las contraseñas no coinciden"
                errorTv.visibility = View.VISIBLE
            } else {
                errorTv.visibility = View.INVISIBLE
                signIn(email.text.toString(), password.text.toString())
            }
        }
    }

    private fun signIn(email: String, password: String) {
        Log.d("INFO", "email: $email, password: $password")
        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    Log.d("INFO", "signInWithEmail: success")
                    val intent = Intent(
                        this,
                        MainActivity::class.java
                    )
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    Log.w("ERROR", "signInWithEmail: failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "El registro falló.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
