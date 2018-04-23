package br.com.rodrigohsb.kyc.recyclerview

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.multiple_choice.view.*
import kotlinx.android.synthetic.main.single_input.view.*
import kotlinx.android.synthetic.main.single_choice.view.*

/**
 * @rodrigohsb
 */
class RecyclerAdapter (private val questions: List<Question>, private val context: Context)
                      : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    private val SINGLE_INPUT = 1
    private val SINGLE_CHOICE = 2
    private val MULTIPLE_CHOICE = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when(viewType) {
            SINGLE_INPUT -> {
                ViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.single_input, parent, false))
            }
            SINGLE_CHOICE -> {
                ViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.single_choice, parent, false))
            }
            MULTIPLE_CHOICE -> {
                ViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.multiple_choice, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item, parent, false))
            }
        }
    }

    override fun getItemCount() = questions.size

    override fun getItemViewType(position: Int): Int {
        val question = questions[position]

        return when(question) {
            is SimpleInputQuestion -> SINGLE_INPUT
            is SimpleChoiceQuestion -> SINGLE_CHOICE
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (holder.itemViewType) {

            SINGLE_INPUT -> {

                val simpleInputQuestion = questions[position] as SimpleInputQuestion
                holder.itemView.single_input_question.text = simpleInputQuestion.question

                RxTextView
                        .textChanges(holder.itemView.single_input_question)
                        .map { charSequence -> !charSequence.isEmpty() }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ hasContent ->
                            when (hasContent){
                                true -> {}
                                false -> {}
                            }
                        })

            }

            SINGLE_CHOICE -> {

                val simpleChoiceQuestion = questions[position] as SimpleChoiceQuestion
                holder.itemView.single_choice_question.text = simpleChoiceQuestion.question

                for(choice in simpleChoiceQuestion.choices){

                    val rb = LayoutInflater.from(context).inflate(R.layout.radio_button, null) as RadioButton
                    rb.id = choice.id.toInt()
                    rb.text = choice.label
                    holder.itemView.single_choice_radio_group.addView(rb)
                }

                RxRadioGroup
                .checkedChanges(holder.itemView.single_choice_radio_group)
                .filter{ radioButtonId -> radioButtonId != -1 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    radioButtonId ->

                    val rb = holder.itemView.single_choice_radio_group.findViewById<RadioButton>(radioButtonId)

                    val answerId = rb.id.toString()
                    val answerLabel = rb.text.toString()
                })
            }

            MULTIPLE_CHOICE -> {
                val multipleChoiceQuestion = questions[position] as MultipleChoiceQuestion
                holder.itemView.multiple_choice_question.text = multipleChoiceQuestion.question

                with(holder.itemView.multiple_choice_recycler_view){
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                    adapter = ChoiceAdapter(multipleChoiceQuestion.choices)
                }

            }
        }
    }
}