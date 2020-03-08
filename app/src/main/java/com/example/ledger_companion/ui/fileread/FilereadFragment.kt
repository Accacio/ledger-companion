package com.example.ledger_companion.ui.fileread

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ledger_companion.R
import java.io.*
import java.lang.StringBuilder
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import androidx.core.net.toUri
import androidx.preference.PreferenceManager
import java.net.URI


class FilereadFragment : Fragment() {

    private lateinit var filereadViewModel: FilereadViewModel
    private lateinit var textView: TextView
    private lateinit var overwriteButton: Button
    private lateinit var defaultLedgerUri: Uri


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filereadViewModel =
            ViewModelProviders.of(this).get(FilereadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_fileread, container, false)
        textView = root.findViewById(R.id.text_fileread)

        textView.movementMethod = ScrollingMovementMethod()
//        overwriteButton.isActivated = false
//        overwriteButton.setOnClickListener { view ->
//            val transactionString = """
//                10/02/2020    Carrefour | fogão
//                expenses:mimimi              €2
//                assets:blewblew              €-2
//
//
//            """.trimIndent()
//            appendToFile(defaultLedgerUri,transactionString)
//            val newText : String =  textView.text.toString() + transactionString
//            textView.text= newText
//
//            }

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(root.context /* Activity context */)
        val namefile = sharedPref.getString("file","")



        if (!namefile.equals("")) {
//            var uri : Uri = namefile!!.toUri()
            Log.i("FileRead", """namefile not empty $namefile""")
            val uri:Uri = Uri.parse(namefile)
            textView.text = readTextFromUri(context,uri)
        }

//        filereadViewModel.text.observe(this, Observer {
//            textView.text = it
//        })



        return root
    }

    @Throws(IOException::class)
    private fun readTextFromUri(context : Context?, uri: Uri): String {
        val stringBuilder = StringBuilder()
        context?.contentResolver?.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append("\n")
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }


    private fun appendToFile(uri: Uri,string : String) {
        try {
            context?.contentResolver?.openFileDescriptor(uri, "wa")?.use {
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