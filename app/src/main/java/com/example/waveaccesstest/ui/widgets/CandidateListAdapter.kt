package com.example.waveaccesstest.ui.widgets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waveaccesstest.R
import com.example.waveaccesstest.model.domain.Candidate

class CandidateListAdapter(
    private val candidates: List<Candidate>,
    private val onCandidateClick: (Long) -> Unit
): RecyclerView.Adapter<CandidateListAdapter.ViewHolder>() {
    private var activeTrue: String? = null
    private var activeFalse: String? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val parent: View
        val name: TextView
        val email: TextView
        val active: TextView


        init {
            parent = view
            name = view.findViewById(R.id.name_text)
            email = view.findViewById(R.id.email_text)
            active = view.findViewById(R.id.active_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidate_list_item, parent, false)

        if (activeTrue == null) activeTrue = parent.context.getString(R.string.candidate_active_true)
        if (activeFalse == null) activeFalse = parent.context.getString(R.string.candidate_active_false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.name.text = candidate.name
        holder.name.isEnabled = true
        holder.email.text = candidate.email
        holder.email.isEnabled = true
        holder.active.text = if (candidate.isActive) activeTrue else activeFalse
        holder.active.isEnabled = true
        if (candidate.isActive) {
            holder.parent.isClickable = true
            holder.parent.setOnClickListener {
                onCandidateClick(candidate.id)
            }
        } else {
            holder.parent.isClickable = false
            holder.name.isEnabled = false
            holder.email.isEnabled = false
            holder.active.isEnabled = false
        }

    }

    override fun getItemCount(): Int = candidates.size
}