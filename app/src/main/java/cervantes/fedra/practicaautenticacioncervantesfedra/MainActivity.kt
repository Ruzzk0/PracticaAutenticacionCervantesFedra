package cervantes.fedra.practicaautenticacioncervantesfedra

import android.content.Intent
import android.os.Bundle
import android.view.View
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

        val email = intent.extras!!.getString("user", "Invitado")

        val userTextView = findViewById<TextView>(R.id.tvUser)
        val logoutButton = findViewById<Button>(R.id.btnLogout)

        userTextView.text = "Bienvenido, $email"

        logoutButton.setOnClickListener { v: View? ->
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(
                this,
                LoginActivity::class.java
            )
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}
