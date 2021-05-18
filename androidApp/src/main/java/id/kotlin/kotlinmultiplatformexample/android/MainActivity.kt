package id.kotlin.kotlinmultiplatformexample.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import id.kotlin.kotlinmultiplatformexample.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        setupView()

    }

    private fun setupObserver() {
        mainViewModel.loginResult.observe(this, { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupView() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            mainViewModel.login(username, password)
        }
    }
}
