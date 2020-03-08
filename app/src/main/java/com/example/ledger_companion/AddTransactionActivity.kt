package com.example.ledger_companion

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import android.widget.TextView
import android.widget.LinearLayout
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.ledger_companion.db.dbHandler
import com.example.ledger_companion.dbModels.Payee


class AddTransactionActivity : AppCompatActivity() {

    lateinit var dbHelper : dbHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        dbHelper = dbHandler(this)
        val db = dbHelper.writableDatabase


        //dbHelper.onUpgrade(db,0,1);
        //dbHelper.AddPayee(db, Payee(0,"Carrefour"))

        val dateEditText : EditText = findViewById(R.id.transactionDate)
        val payeeEditText : AutoCompleteTextView = findViewById(R.id.transactionPayee)
        val commentEditText : AutoCompleteTextView = findViewById(R.id.transactionComment)

        dateEditText.setOnClickListener{ view ->
            val dialog = DatePickerDialog(this, { view, year, month, dayOfMonth ->  dateEditText.setText(resources.getString(R.string.date, year, month,dayOfMonth)) },
                Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)+1,Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
            dialog.show()
        }
        val payeesAdapter : ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,dbHelper.getAllPayees(db));
        val notesAdapter : ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, getComments());

        payeeEditText.setAdapter(payeesAdapter);
        payeeEditText.threshold = 0
        commentEditText.setAdapter(notesAdapter);
        commentEditText.threshold = 0

//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.addTransactionActivity, SettingsActivity.SettingsFragment())
//            .commit()
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        toolbar. = "Add Transaction"
//        val fab: FloatingActionButton = findViewById(R.id.fab)
        //TODO colocar botão para visualizar
        // TODO: verificar se soma igual a 0 ou último igual a ""
        // se for igual "" colocar a soma dos outros
// TODO: Verificar esse código para criar automaticamente partes da transação
//        val textArray = arrayOf("One", "Two", "Three", "Four")
//        val linearLayout = LinearLayout(this)
//        setContentView(linearLayout)
//        linearLayout.orientation = LinearLayout.VERTICAL
//        for (i in textArray.indices) {
//            val textView = TextView(this)
//            textView.text = textArray[i]
//            linearLayout.addView(textView)
//        }




    }

    fun getPayees() : Array<String> {
        //TODO: get values from file like in a sed command
        return arrayOf("Free", "Carrefour", "France", "Germany", "Spain")
    }

    fun getComments() : Array<String> {
        //TODO: get values from file like in a sed command
        return arrayOf("Forno", "Microondas", "Monitor", "Germany", "Spain")
    }

    private fun appendToFile(uri: Uri, string : String) {
        try {
            contentResolver?.openFileDescriptor(uri, "wa")?.use {
                FileOutputStream(it.fileDescriptor).use {
                    it.write(string.toByteArray())
                }
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
