package uz.turgunboyevjurabek.firebase_register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import uz.turgunboyevjurabek.firebase_register.databinding.ActivityMain2Binding
import java.util.concurrent.TimeUnit

private const val TAG="MainActivity2"
class MainActivity2 : AppCompatActivity() {
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var handler: Handler
    var count:Int=0
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
        handler.postDelayed(runneble,1000)

        sendVerificationCode(number)

        binding.edtPossword.addTextChangedListener {
            verifyCode()
        }

        binding.refreshing.setOnClickListener {
            binding.edtPossword.text?.clear()
            sendVerificationCode(number)
            binding.thtTime.visibility=View.VISIBLE
            binding.refreshing.visibility=View.GONE
            count=0
        }
        //Kelgan smsni avtomatik o'qib olish
        binding.edtPossword.setOnEditorActionListener { v, actionId, event ->
        if (actionId==EditorInfo.IME_ACTION_DONE){
            verifyCode()
            val view=currentFocus
            if (view!=null){
                val imm:InputMethodManager=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken,0)
            }
        }
            true
        }

    }
    val runneble=object :Runnable{
        override fun run() {
            if (count<10){
                binding.thtTime.text="00:0${count.toString()}"
            }else{
                binding.thtTime.text="00:${count.toString()}"
            }
            count++
            handler.postDelayed(this,1000)
            if (count>=60){
                binding.thtTime.visibility=View.GONE
                binding.refreshing.visibility=View.VISIBLE
            }
        }

    }
    fun sendVerificationCode(phoneNumber:String){
        val options=PhoneAuthOptions.newBuilder()
            .setActivity(this)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callback=object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted: Uraaa")
            Toast.makeText(this@MainActivity2, "callback", Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(this@MainActivity2, "No callback ${p0.message}", Toast.LENGTH_LONG).show()
            Log.d(TAG, "onVerificationCompleted:Failed",p0)
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            Log.d(TAG, "onCodeSent: Kod jo'natilidi")
            storedVerificationId=p0
            resentToken=p1
        }
    }
    private fun verifyCode(){
        val code=binding.edtPossword.text.toString()
        if (code.length==6){
            binding.edtPossword.clearFocus()
            val credential=PhoneAuthProvider.getCredential(storedVerificationId,code)
            signInWithPhoneAuthCredential(credential)
        }
    }
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    binding.thtTime.visibility=View.GONE
                    Toast.makeText(this, "Mufaqqiyatli", Toast.LENGTH_SHORT).show()
                    val number=intent.getStringExtra("key_number")
                    val intent= Intent(this,MainActivity3::class.java)
                    intent.putExtra("key_number2",number)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Mufaqiyatsiz", Toast.LENGTH_SHORT).show()
                    if (it.exception is FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(this, "Kod hato kiritildi tekshirib qayta kiriting", Toast.LENGTH_SHORT).show()
                    }

                }
            }
    }

}