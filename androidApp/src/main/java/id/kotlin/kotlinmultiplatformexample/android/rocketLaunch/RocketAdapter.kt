package id.kotlin.kotlinmultiplatformexample.android.rocketLaunch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.kotlin.kotlinmultiplatformexample.android.R
import id.kotlin.kotlinmultiplatformexample.android.databinding.ItemRocketBinding
import id.kotlin.kotlinmultiplatformexample.data.remote.RocketLaunch

class RocketAdapter(private val rockets: List<RocketLaunch>) : RecyclerView.Adapter<RocketAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemRocketBinding) : RecyclerView.ViewHolder(binding.root) {
        var missionName = binding.missionName
        var launcherYear = binding.launchYear
        var launchSuccess = binding.launchSuccess
        var detail = binding.details

        fun bind(data: RocketLaunch) {
            missionName.text = data.missionName
            launcherYear.text = data.launchYear.toString()
            detail.text = data.details ?: ""
            val status = data.launchSuccess
            if (status != null) {
                if (status) {
                    launchSuccess.text = "Successful"
                    launchSuccess.setTextColor(
                        (ContextCompat.getColor(
                            itemView.context,
                            R.color.green
                        ))
                    )
                } else {
                    launchSuccess.text = "Unsuccessful"
                    launchSuccess.setTextColor(
                        (ContextCompat.getColor(
                            itemView.context,
                            R.color.red
                        ))
                    )
                }
            } else {
                launchSuccess.text = "No data"
                launchSuccess.setTextColor((ContextCompat.getColor(itemView.context, R.color.grey)))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRocketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rockets[position])
    }

    override fun getItemCount(): Int = rockets.size
}