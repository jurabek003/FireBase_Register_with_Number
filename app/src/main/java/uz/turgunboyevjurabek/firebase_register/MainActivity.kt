package uz.turgunboyevjurabek.firebase_register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import uz.turgunboyevjurabek.firebase_register.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            if (binding.edtNumber.text.toString().length==13){
                val intent=Intent(this,MainActivity2::class.java)
                intent.putExtra("key_number",binding.edtNumber.text.toString())
                startActivity(intent)
            }else if(binding.edtNumber.text.toString().length>13){
                Toast.makeText(this, "Ko'p raqam yozib yubordingiz iltimos tekshirib qayata urinib ko'ring! ", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Yetarlicha raqam kiritmadingiz  iltimos tekshirib qayata urinib ko'ring! ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}