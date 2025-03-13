package cervantes.fedra.practicaautenticacioncervantesfedra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Obtiene el correo del intent
        val email = intent.extras?.getString("user") ?: "Invitado"

        // Referencias a los elementos de la UI
        val userTextView: TextView = findViewById(R.id.tvUser)
        val logoutButton: Button = findViewById(R.id.btnLogout)

        // Establece el texto de bienvenida
        userTextView.text = "Bienvenido, $email"

        // Lógica para cerrar sesión
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Finaliza la actividad para que no se pueda volver atrás
        }
    }
}
