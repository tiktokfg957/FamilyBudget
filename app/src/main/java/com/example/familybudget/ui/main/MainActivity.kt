package com.example.familybudget.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.familybudget.databinding.ActivityMainBinding
import com.example.familybudget.ui.add.AddTransactionActivity
import com.example.familybudget.MyApplication

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository by lazy { (application as MyApplication).repository }
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(repository)
    }
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Семейный бюджет"

        adapter = TransactionAdapter { transaction ->
            // пока просто показываем тост, потом можно сделать редактирование
        }

        binding.rvTransactions.layoutManager = LinearLayoutManager(this)
        binding.rvTransactions.adapter = adapter

        viewModel.transactions.observe(this) { transactions ->
            adapter.submitList(transactions)
        }

        viewModel.balance.observe(this) { balance ->
            binding.tvBalance.text = "${balance / 100.0} ₽"
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }
    }
}
