package br.com.rodrigohsb.kyc.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.comp_base.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_view)

        val questions = ArrayList<Question>()

        val choices = ArrayList<Choice>()
        choices.add(Choice("1","Masculino"))
        choices.add(Choice("2","Feminino"))
        choices.add(Choice("3","Outro"))

        questions.add(SimpleInputQuestion("Qual seu nome?"))
        questions.add(SimpleChoiceQuestion("Qual seu gênero?", choices))
        questions.add(SimpleInputQuestion("Quantos anos vc tem?"))
        questions.add(SimpleInputQuestion("Qual seu cep?"))

        val choices1 = ArrayList<Choice>()
        choices1.add(Choice("1","Solteiro"))
        choices1.add(Choice("2","Casado"))
        choices1.add(Choice("3","Divorciado"))
        choices1.add(Choice("4","Viuvo"))
        choices1.add(Choice("5","Separado"))

        questions.add(SimpleChoiceQuestion("Qual seu estado civil?", choices1))

        with(recycler_view){
            setHasFixedSize(true)
            layoutManager = object: LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false) {
//                override fun canScrollHorizontally() = false
            }
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

            adapter = RecyclerAdapter(questions,this@MainActivity)
        }

    }
}
