package com.amoronk.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.amoronk.android.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val navController = Navigation.findNavController(this@MainActivity, R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar?.setNavigationIcon(R.drawable.ic_back)
            val isHomeScreenActive = destination.id == R.id.nav_home
            val isDetailsScreenActive = destination.id == R.id.nav_details
            val isFavScreenActive = destination.id == R.id.nav_fav
            toolbar.isInvisible = isHomeScreenActive


            (isDetailsScreenActive || isFavScreenActive).let {
                home_title.isInvisible = it
                homeFavImageButton.isInvisible = it
            }
        }

        toolbar.setNavigationOnClickListener {
            navController.navigateUp() || super.onSupportNavigateUp()
        }


    }
}