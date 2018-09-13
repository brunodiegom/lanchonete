package com.brunodiegom.lanchonete.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.brunodiegom.lanchonete.R
import com.brunodiegom.lanchonete.model.Ingredients
import com.brunodiegom.lanchonete.server.InitializerListener
import com.brunodiegom.lanchonete.view.fragment.CartFragment
import com.brunodiegom.lanchonete.view.fragment.MenuFragment
import com.brunodiegom.lanchonete.view.fragment.OfferFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(),
        InitializerListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private val ingredients: Ingredients by inject()

    private val menuFragment = MenuFragment()
    private val offerFragment = OfferFragment()
    private val cartFragment = CartFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ingredients.setListener(this)
        navigation_view.setOnNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (ingredients.isInitialized) {
            navigation_view.selectedItemId = R.id.menu
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> commitFragment(menuFragment)
            R.id.offer -> commitFragment(offerFragment)
            R.id.cart -> commitFragment(cartFragment)
        }
        return true
    }

    override fun onInitializeFinish() {
        navigation_view.selectedItemId = R.id.menu
    }

    private fun commitFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
