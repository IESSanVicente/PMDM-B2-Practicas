package es.javiercarrasco.myimcv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import es.javiercarrasco.myimcv2.databinding.ActivityHistoricoBinding
import es.javiercarrasco.myimcv2.utils.MyFunctions

class HistoricoActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoricoBinding
    private val myAdapter: MyRecyclerAdapter = MyRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvInfo.isVisible = true

        if (fileList().contains(getString(R.string.filename))) {
            binding.tvInfo.isVisible = false
            myAdapter.MyRecyclerAdapter(this, MyFunctions().readFile(this))
            binding.rvIMCs.setHasFixedSize(true)
            binding.rvIMCs.layoutManager = LinearLayoutManager(this)
            binding.rvIMCs.adapter = myAdapter
        }
    }
}