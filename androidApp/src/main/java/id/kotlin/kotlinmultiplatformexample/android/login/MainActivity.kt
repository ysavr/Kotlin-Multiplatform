package id.kotlin.kotlinmultiplatformexample.android.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import id.kotlin.kotlinmultiplatformexample.android.rocketLaunch.RocketLaunchActivity
import id.kotlin.kotlinmultiplatformexample.android.databinding.ActivityMainBinding
import id.kotlin.kotlinmultiplatformexample.data.Result

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
        setupView()

    }

    private fun setupObserver() {
        mainViewModel.loginResult.observe(this, { result ->
            var status = ""
            if (result is Result.Success) {
                status = "Hello ${result.data.name}"
                startActivity(Intent(this, RocketLaunchActivity::class.java))
            } else if (result is Result.Error) {
                status = result.exception.message.toString()
            }
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })
    }

    @ExperimentalStdlibApi
    private fun setupView() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            mainViewModel.login(username, password)
        }
    }
}
