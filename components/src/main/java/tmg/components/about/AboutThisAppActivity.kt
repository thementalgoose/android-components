package tmg.components.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.about_this_app_activity.*
import tmg.components.R

class AboutThisAppActivity: AppCompatActivity(),
    AboutThisAppCallback {

    private lateinit var configuration: AboutThisAppConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_this_app_activity)

        configuration = intent.extras?.getParcelable(keyConfiguration) ?: throw RuntimeException("Please provide an 'AboutThisAppConfiguration' item (via. AboutThisAppActivity.intent(context, AboutThisAppConfiguration))")

        aboutThisApp_backButton.setOnClickListener {
            onBackPressed()
        }

        if (configuration.imageUrl != null) {
            Glide.with(this)
                .load(configuration.imageUrl)
                .into(aboutThisApp_icon)
        }
        else if (configuration.imageRes != null) {
            Glide.with(this)
                .load(configuration.imageRes)
                .into(aboutThisApp_icon)
        }

        aboutThisApp_name.text = configuration.name
        aboutThisApp_nameToolbar.text = configuration.name
        aboutThisApp_nameDesc.text = configuration.nameDesc

        val adapter = AboutThisAppAdapter(this)
        aboutThisApp_list.adapter = adapter
        aboutThisApp_list.layoutManager = LinearLayoutManager(this)

        adapter.items = populateList()
    }

    private fun populateList(): List<AboutThisAppItem> {
        val list: MutableList<AboutThisAppItem> = mutableListOf()

        list.add(AboutThisAppItem.Header(
            play = configuration.playStore,
            email = configuration.email,
            website = configuration.website,
            github = configuration.github
        ))

        configuration.subtitle?.let {
            list.add(AboutThisAppItem.Message(
                msg = it
            ))
        }

        list.addAll(configuration.dependencies.map {
            AboutThisAppItem.Dependency(it)
        })

        configuration.footnote?.let {
            list.add(AboutThisAppItem.Message(
                msg = it,
                isCentered = false
            ))
        }

        list.add(AboutThisAppItem.Message(getString(R.string.about_this_app_app_version, configuration.appVersion)))
        return list
    }

    //region AboutThisAppDependencyCallback

    override fun dependencyItemClicked(item: AboutThisAppDependency) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
    }

    override fun clickPlay() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(configuration.playStore))
        startActivity(intent)
    }

    override fun clickEmail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, configuration.email)
        intent.putExtra(Intent.EXTRA_SUBJECT, configuration.appName)
        startActivity(Intent.createChooser(intent, getString(R.string.about_this_app_send_email)))
    }

    override fun clickWebsite() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(configuration.website)))
    }

    override fun clickGithub() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(configuration.github)))
    }

    //endregion

    companion object {

        private const val keyConfiguration: String = "configuration"

        @JvmStatic
        fun intent(context: Context, configuration: AboutThisAppConfiguration): Intent {
            val intent = Intent(
                context,
                AboutThisAppActivity::class.java
            )
            intent.putExtra(keyConfiguration, configuration)
            return intent
        }
    }
}