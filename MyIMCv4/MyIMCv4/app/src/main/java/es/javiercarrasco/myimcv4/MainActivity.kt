package es.javiercarrasco.myimcv4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myimcv4.adapters.MyRecyclerAdapter
import es.javiercarrasco.myimcv4.adapters.ViewPagerAdapter
import es.javiercarrasco.myimcv4.databinding.ActivityMainBinding
import es.javiercarrasco.myimcv4.utils.MyIMCDBOpenHelper

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var imcDBHelper: MyIMCDBOpenHelper
        val myAdapter: MyRecyclerAdapter = MyRecyclerAdapter()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se instancia el Helper de la BD.
        imcDBHelper = MyIMCDBOpenHelper(this, null)

        // Se carga la toolbar.
        setSupportActionBar(binding.toolbar)

        // Se crea el adapter.
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Se añaden los fragments y los títulos de pestañas.
        adapter.addFragment(ImcFragment(), "IMC")
        adapter.addFragment(HistoricoFragment(), "Histórico")

        // Se asocia el adapter.
        binding.viewPager.adapter = adapter

        // Se cargan las tabs.
        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}