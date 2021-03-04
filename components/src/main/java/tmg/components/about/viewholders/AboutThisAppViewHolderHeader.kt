package tmg.components.about.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import tmg.components.R
import tmg.components.about.AboutThisAppCallback
import tmg.components.about.AboutThisAppItem
import tmg.components.utils.show

internal class AboutThisAppViewHolderHeader(
    private val callback: AboutThisAppCallback,
    itemView: View
): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val email: TextView = itemView.findViewById(R.id.aboutThisAppLinks_email)
    private val github: TextView = itemView.findViewById(R.id.aboutThisAppLinks_github)
    private val play: TextView = itemView.findViewById(R.id.aboutThisAppLinks_play)
    private val website: TextView = itemView.findViewById(R.id.aboutThisAppLinks_website)

    init {
        email.setOnClickListener(this)
        github.setOnClickListener(this)
        play.setOnClickListener(this)
        website.setOnClickListener(this)
    }

    fun bind(item: AboutThisAppItem.Header) {
        email.show(item.email != null)
        github.show(item.github != null)
        play.show(item.play != null)
        website.show(item.website != null)
    }

    override fun onClick(v: View?) {
        when (v) {
            email -> {
                callback.clickEmail()
            }
            github -> {
                callback.clickGithub()
            }
            play -> {
                callback.clickPlay()
            }
            website -> {
                callback.clickWebsite()
            }
        }
    }
}