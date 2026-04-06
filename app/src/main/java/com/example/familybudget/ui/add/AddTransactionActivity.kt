package com.example.familybudget.ui.add

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.familybudget.MyApplication
import com.example.familybudget.data.model.Category
import com.example.familybudget.data.model.Transaction
import com.example.familybudget.data.repository.BudgetRepository
import com.example.familybudget.databinding.ActivityAddTransactionBinding
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var repository: BudgetRepository
    private var selectedCategory: Category? = null
    private var categories: List<Category> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as MyApplication).repository

        loadCategories()

        binding.btnSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun loadCategories() {
        lifecycleScope.launch {
            repository.getAllCategories().collect { list ->
                categories = list
                val adapter = ArrayAdapter(this@AddTransactionActivity, android.R.layout.simple_spinner_item, list.map { it.name })
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCategory.adapter = adapter
                selectedCategory = list.firstOrNull()
                binding.spinnerCategory.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                        selectedCategory = list[position]
                    }
                    override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
                }
            }
        }
    }

    private fun saveTransaction() {
        val amountStr = binding.etAmount.text.toString().trim()
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT).show()
            return
        }
        val amount = (amountStr.toDoubleOrNull() ?: 0.0) * 100
        if (amount <= 0) {
            Toast.makeText(this, "Сумма должна быть больше 0", Toast.LENGTH_SHORT).show()
            return
        }
        val type = if (binding.rbExpense.isChecked) "expense" else "income"
        val categoryId = selectedCategory?.id ?: 0
        if (categoryId == 0L) {
            Toast.makeText(this, "Выберите категорию", Toast.LENGTH_SHORT).show()
            return
        }

        val transaction = Transaction(
            amount = amount.toLong(),
            categoryId = categoryId,
            date = System.currentTimeMillis(),
            type = type,
            note = null
        )
        lifecycleScope.launch {
            repository.insertTransaction(transaction)
            Toast.makeText(this@AddTransactionActivity, "Сохранено", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
