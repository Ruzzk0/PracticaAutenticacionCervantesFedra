package cervantes.fedra.practicaautenticacioncervantesfedra

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        onStart()

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val errorTv = findViewById<TextView>(R.id.tvError)
        val button = findViewById<Button>(R.id.btnLogin)
        val register = findViewById<Button>(R.id.btnGoRegister)

        errorTv.visibility = View.INVISIBLE

        button.setOnClickListener { v: View? ->
            login(
                email.text.toString(),
                password.text.toString()
            )
        }

        register.setOnClickListener { v: View? ->
            val intent = Intent(
                this,
                RegisterActivity::class.java
            )
            startActivity(intent)
        }
    }

    fun goToMain(user: FirebaseUser) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user.email)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun showError(text: String?, visible: Boolean) {
        val errorTv = findViewById<TextView>(R.id.tvError)
        errorTv.text = text
        errorTv.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = auth?.currentUser
        if (currentUser != null) {
            goToMain(currentUser)
        }
    }

    fun login(email: String?, password: String?) {
        auth?.signInWithEmailAndPassword(email ?: "", password ?: "")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth?.currentUser
                    if (user != null) {
                        showError("", false)
                        goToMain(user)
                    }
                } else {
                    showError("Usuario y/o contraseña equivocados", true)
                }
            }
    }
}
