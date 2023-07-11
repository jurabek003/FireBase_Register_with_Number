package uz.turgunboyevjurabek.firebase_register

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import uz.turgunboyevjurabek.firebase_register.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var handler: Handler
    lateinit var resentToken: PhoneAuthProvider.ForceResendingToken
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        val number=intent.getStringExtra("key_number")
        val kod1= number!![4].toString()
        val kod2= number[5].toString()
        val kod3= number[6].toString()
        val kod4= number[7].toString()
        val kod5= number[8].toString()
        binding.thtNumber.text="Bir martalik kod  (+99 8${kod1+kod2}) ${kod3+kod4+kod5}-**-**\n" +
                "raqamiga yuborildi"

        handler= Handler(Looper.getMainLooper())
        handler.postDelayed(runneble,3000)
        if (count==60){
            stopLockTask()
        }

    }
    var count=0
    val runneble=object :Runnable{
        override fun run() {
            if (count<10){
                binding.thtTime.text="00:0${count.toString()}"
            }else{
                binding.thtTime.text="00:${count.toString()}"
            }
            count++
            handler.postDelayed(this,1000)
        }

    }

}