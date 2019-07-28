package com.example.lister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lister.ui.login.LoginActivity
import com.google.android.material.navigation.NavigationView

class ScrollingActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    class OneList(
        val owner: String,
        val name: String,
        val content: String,
        val date: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!PreferenceManager(this).preferences.getBoolean("logged", false))
        {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_scrolling)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        val listsHolder: LinearLayout = findViewById(R.id.lists_holder)
        val listData = OneList("kotek", "jedzenie", "mleko", "12-12-1212 12:12")
        val listLayout: View = LayoutInflater.from(this).inflate(R.layout.list, listsHolder)
        listLayout.findViewById<TextView>(R.id.list_header).text = listData.name
        listLayout.findViewById<TextView>(R.id.list_content).text = listData.content

        val listData2 = OneList("lama", "lama", "lama", "12-12-1212 12:12")
        val listLayout2: View = LayoutInflater.from(this).inflate(R.layout.list, listsHolder)
        listLayout2.findViewById<TextView>(R.id.list_header).text = listData2.name
        listLayout2.findViewById<TextView>(R.id.list_content).text = listData2.content

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_all_lists -> {
                val intent = Intent(this, ScrollingActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_new_list -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_followed -> {

            }
            R.id.nav_groups -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        this.finish()
        return true
    }
}
