package com.example.familybudget.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.familybudget.databinding.ActivityMainBinding
import com.example.familybudget.ui.add.AddTransactionActivity
import com.example.familybudget.ui.statistics.StatisticsActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((application as com.example.familybudget.FamilyBudgetApplication).repository)
    }
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        observeData()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = TransactionAdapter { transaction ->
            lifecycleScope.launch {
                viewModel.deleteTransaction(transaction)
            }
        }
        binding.rvTransactions.layoutManager = LinearLayoutManager(this)
        binding.rvTransactions.adapter = adapter
    }

    private fun observeData() {
        viewModel.transactions.observe(this) { transactions ->
            adapter.submitList(transactions)
        }
        viewModel.totalIncome.observe(this) { income ->
            binding.tvTotalIncome.text = String.format("%.2f ₽", income)
            updateBalance()
        }
        viewModel.totalExpense.observe(this) { expense ->
            binding.tvTotalExpense.text = String.format("%.2f ₽", expense)
            updateBalance()
        }
    }

    private fun updateBalance() {
        val balance = (viewModel.totalIncome.value ?: 0.0) - (viewModel.totalExpense.value ?: 0.0)
        binding.tvBalance.text = String.format("%.2f ₽", balance)
    }

    private fun setupListeners() {
        binding.btnAddTransaction.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }
    }
}
