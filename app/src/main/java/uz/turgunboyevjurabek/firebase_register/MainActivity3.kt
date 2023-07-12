package uz.turgunboyevjurabek.firebase_register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.turgunboyevjurabek.firebase_register.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    private val binding by lazy { ActivityMain3Binding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val number2=intent.getStringExtra("key_number2")
        val kod1= number2!![4].toString()
        val kod2= number2[5].toString()
        val kod3= number2[6].toString()
        val kod4= number2[7].toString()
        val kod5= number2[8].toString()
        val kod6= number2[9].toString()
        val kod7= number2[10].toString()
        val kod8= number2[11].toString()
        val kod9= number2[12].toString()

        binding.thtNumber2.text="(+99 8${kod1+kod2}) ${kod3+kod4+kod5}-${kod6+kod7}-${kod8+kod9}"

    }
}