package com.chahine_elati.words.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chahine_elati.words.LetterListFragmentDirections
import com.chahine_elati.words.MainActivity
import com.chahine_elati.words.R
import com.chahine_elati.words.WordListFragment

class LetterAdapter :
    RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {

    private val list = ('A').rangeTo('Z').toList()

    class LetterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        layout.accessibilityDelegate = Accessibility
        return LetterViewHolder(layout)
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val item = list[position]
        holder.button.text = item.toString()
        holder.button.setOnClickListener {
            val action = LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(letter = holder.button.text.toString())
            holder.view.findNavController().navigate(action)
        }
    }

    companion object Accessibility : View.AccessibilityDelegate() {

        override fun onInitializeAccessibilityNodeInfo(
            host: View?,
            info: AccessibilityNodeInfo?,
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)

            val customString = host?.context?.getString(R.string.look_up_words)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }
}