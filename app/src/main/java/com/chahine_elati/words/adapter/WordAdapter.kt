package com.chahine_elati.words.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.chahine_elati.words.R
import com.chahine_elati.words.WordListFragment

class WordAdapter(private val letterId: String, context: Context) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val filteredWords: List<String>

    init {
        // Retrieve the list of words from res/values/arrays.xml
        val words = context.resources.getStringArray(R.array.words).toList()

        filteredWords = words
            // Returns items in a collection if the conditional clause is true,
            // in this case if an item starts with the given letter,
            // ignoring UPPERCASE or lowercase.
            .filter { it.startsWith(letterId, ignoreCase = true) }
            // Returns a collection that it has shuffled in place
            .shuffled()
            // Returns the first n items as a [List]
            .take(5)
            // Returns a sorted version of that [List]
            .sorted()
    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button_item)
    }

    override fun getItemCount(): Int = filteredWords.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        layout.accessibilityDelegate = Accessibility

        return WordViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val item = filteredWords[position]

        val context = holder.view.context

        holder.button.text = item
        holder.button.setOnClickListener {
            val queryUrl: Uri = Uri.parse("${WordListFragment.SEARCH_PREFIX}${item}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context.startActivity(intent)
        }

    }

    companion object Accessibility : View.AccessibilityDelegate() {
        
        override fun onInitializeAccessibilityNodeInfo(
            host: View?,
            info: AccessibilityNodeInfo?,
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)

            val customString = host?.context?.getString(R.string.look_up_word)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }
}