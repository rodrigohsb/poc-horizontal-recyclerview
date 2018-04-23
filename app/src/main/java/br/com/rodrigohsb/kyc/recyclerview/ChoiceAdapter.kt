package br.com.rodrigohsb.kyc.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.choice_row.view.*

/**
 * @rodrigohsb
 */
class ChoiceAdapter (private val choices: List<Choice>)
                      : RecyclerView.Adapter<ChoiceAdapter.ViewHolder>(){

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =

        ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.radio_button, parent, false))

    override fun getItemCount() = choices.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val choice = choices[position]

        holder.itemView.rb.id = choice.id.toInt()
        holder.itemView.rb.text = choice.label
    }
}