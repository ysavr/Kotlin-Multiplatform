package id.kotlin.kotlinmultiplatformexample.android.rocketLaunch

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import id.kotlin.kotlinmultiplatformexample.android.databinding.ActivityRocketLaunchBinding
import id.kotlin.kotlinmultiplatformexample.android.databinding.ContentRocketLaunchBinding
import id.kotlin.kotlinmultiplatformexample.android.utils.Resource
import id.kotlin.kotlinmultiplatformexample.db.DatabaseDriverFactory

class RocketLaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRocketLaunchBinding
    private lateinit var content: ContentRocketLaunchBinding

    private var progressDialog: ProgressDialog? = null
    private val viewModel: RocketViewModel by viewModels()
    private val database = DatabaseDriverFactory(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRocketLaunchBinding.inflate(layoutInflater)
        content = binding.contentRocketLaunch
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getRockets(database)

        setupObserver()
        setupView()

    }

    private fun setupObserver() {
        viewModel.rockets.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    progressDialog?.setCancelable(false)
                    progressDialog?.setMessage("Requesting...")
                    progressDialog?.show()

                }
                Resource.Status.ERROR -> {
                    progressDialog?.dismiss()
                    Toast.makeText(applicationContext, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.SUCCESS -> {
                    progressDialog?.dismiss()
                    if (it.data?.isEmpty() == true) {
                        binding.contentRocketLaunch.rvRocket.visibility = View.GONE
                        Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.contentRocketLaunch.rvRocket.visibility = View.VISIBLE
                        val adapter = RocketAdapter(it.data!!)
                        binding.contentRocketLaunch.rvRocket.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun setupView() {
        progressDialog = ProgressDialog(this)
    }

}