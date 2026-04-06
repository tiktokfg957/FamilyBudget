package com.example.familybudget.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.familybudget.data.model.Transaction
import com.example.familybudget.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(
    private val onLongClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private var transactions = listOf<Transaction>()
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun submitList(list: List<Transaction>) {
        transactions = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transactions[position])
        holder.itemView.setOnLongClickListener {
            onLongClick(transactions[position])
            true
        }
    }

    override fun getItemCount() = transactions.size

    inner class ViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.tvCategory.text = transaction.category
            binding.tvNote.text = transaction.note ?: ""
            val sign = if (transaction.type == "income") "+" else "-"
            val color = if (transaction.type == "income") android.graphics.Color.parseColor("#4CAF50") else android.graphics.Color.parseColor("#F44336")
            binding.tvAmount.text = String.format("%s%.2f ₽", sign, transaction.amount)
            binding.tvAmount.setTextColor(color)
            binding.tvDate.text = dateFormat.format(Date(transaction.date))
        }
    }
}
