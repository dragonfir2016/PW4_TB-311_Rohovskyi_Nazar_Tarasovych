package com.example.laboratory_4

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentTask1(), "Завдання 1")
        adapter.addFragment(FragmentTask2(), "Завдання 2")
        adapter.addFragment(FragmentTask3(), "Завдання 3")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    class FragmentTask1 : Fragment(R.layout.fragment_task1) {

        override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val inputFields = arrayOf(
                view.findViewById<EditText>(R.id.inputSm),
                view.findViewById<EditText>(R.id.inputUnom),
                view.findViewById<EditText>(R.id.inputT),
                view.findViewById<EditText>(R.id.inputIk),
                view.findViewById<EditText>(R.id.inputTf)
            )

            val calculateButton = view.findViewById<Button>(R.id.calculateButton)
            val resultsTextView = view.findViewById<TextView>(R.id.resultsTextView)

            calculateButton.setOnClickListener {
                try {
                    val Sm = inputFields[0].text.toString().toDouble()
                    val Unom = inputFields[1].text.toString().toDouble()
                    val T = inputFields[2].text.toString().toDouble()
                    val Ik = inputFields[3].text.toString().toDouble()
                    val Tf = inputFields[4].text.toString().toDouble()

                    val Im = Sm / (Math.sqrt(3.0) * Unom)
                    val thermalResistance = Ik * Ik * Tf

                    resultsTextView.text = """
                        Трифазний струм: ${"%.2f".format(Im)} A
                        Тепловий опір: ${"%.2f".format(thermalResistance)} Дж
                    """.trimIndent()
                } catch (e: Exception) {
                    resultsTextView.text = "Помилка: перевірте введені дані."
                }
            }
        }
    }

    class FragmentTask2 : Fragment(R.layout.fragment_task2) {

        override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val inputFields = arrayOf(
                view.findViewById<EditText>(R.id.inputUc),
                view.findViewById<EditText>(R.id.inputSk),
                view.findViewById<EditText>(R.id.inputXt),
                view.findViewById<EditText>(R.id.inputTransformerImpedance)
            )

            val calculateButton = view.findViewById<Button>(R.id.calculateButton2)
            val resultsTextView = view.findViewById<TextView>(R.id.resultsTextView2)

            calculateButton.setOnClickListener {
                try {
                    val Uc = inputFields[0].text.toString().toDouble()
                    val Sk = inputFields[1].text.toString().toDouble()
                    val Xt = inputFields[2].text.toString().toDouble()
                    val transformerImpedance = inputFields[3].text.toString().toDouble()

                    val Xtotal = Xt + transformerImpedance
                    val Ik = Uc / (Math.sqrt(3.0) * Xtotal)

                    resultsTextView.text = """
                        Сумарний опір: ${"%.2f".format(Xtotal)} Ом
                        Трифазний струм КЗ: ${"%.2f".format(Ik)} A
                    """.trimIndent()
                } catch (e: Exception) {
                    resultsTextView.text = "Помилка: перевірте введені дані."
                }
            }
        }
    }

    class FragmentTask3 : Fragment(R.layout.fragment_task3) {

        override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            val inputFields = arrayOf(
                view.findViewById<EditText>(R.id.inputRcNormal),
                view.findViewById<EditText>(R.id.inputXtNormal),
                view.findViewById<EditText>(R.id.inputRcMinimal),
                view.findViewById<EditText>(R.id.inputXtMinimal),
                view.findViewById<EditText>(R.id.inputUnom)
            )

            val calculateButton = view.findViewById<Button>(R.id.calculateButton3)
            val resultsTextView = view.findViewById<TextView>(R.id.resultsTextView3)

            calculateButton.setOnClickListener {
                try {
                    val RcNormal = inputFields[0].text.toString().toDouble()
                    val XtNormal = inputFields[1].text.toString().toDouble()
                    val RcMinimal = inputFields[2].text.toString().toDouble()
                    val XtMinimal = inputFields[3].text.toString().toDouble()
                    val Unom = inputFields[4].text.toString().toDouble()

                    val ZNormal = Math.sqrt(RcNormal * RcNormal + XtNormal * XtNormal)
                    val IkNormal = Unom / (Math.sqrt(3.0) * ZNormal)

                    val ZMinimal = Math.sqrt(RcMinimal * RcMinimal + XtMinimal * XtMinimal)
                    val IkMinimal = Unom / (Math.sqrt(3.0) * ZMinimal)

                    resultsTextView.text = """
                        Нормальний режим:
                        Сумарний опір: ${"%.2f".format(ZNormal)} Ом
                        Струм КЗ: ${"%.2f".format(IkNormal)} A

                        Мінімальний режим:
                        Сумарний опір: ${"%.2f".format(ZMinimal)} Ом
                        Струм КЗ: ${"%.2f".format(IkMinimal)} A
                    """.trimIndent()
                } catch (e: Exception) {
                    resultsTextView.text = "Помилка: перевірте введені дані."
                }
            }
        }
    }

    class ViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {
        private val fragmentList = mutableListOf<Fragment>()
        private val fragmentTitleList = mutableListOf<String>()

        override fun getItem(position: Int): Fragment = fragmentList[position]

        override fun getCount(): Int = fragmentList.size

        override fun getPageTitle(position: Int): CharSequence = fragmentTitleList[position]

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }
    }
}
