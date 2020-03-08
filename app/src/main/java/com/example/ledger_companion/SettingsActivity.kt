package com.example.ledger_companion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager


class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPref : SharedPreferences
    private lateinit var selectFileButton: Button
    private lateinit var mydatabase: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        selectFileButton = findViewById(R.id.selectFile)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
        val namefile = sharedPref.getString("file","")
        if (!namefile.equals(""))
        {
            Log.i("Teste",namefile)
            selectFileButton.text = namefile
        }
            selectFileButton.setOnClickListener { view ->

                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "*/*"

                    // Optionally, specify a URI for the file that should appear in the
                    // system file picker when it loads.
    //            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
                }

                startActivityForResult(intent, 2)
            }

        mydatabase = openOrCreateDatabase("ledger", Context.MODE_PRIVATE, null)


    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == 2
            && resultCode == Activity.RESULT_OK) {

            // The result data contains a URI for the document or directory that
            // the user selected.
            resultData?.data?.also { uri ->

                val ext : String = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
                if (ext=="ledger")
                {

                    val sharedPref = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
//                    with (sharedPref.edit()) {
//                        putString("file",uri.toString())
//                        commit()
//                    }
                    sharedPref.edit() {
                        putString("file",uri.toString())
                        commit()
                    }
                    val namefile = sharedPref.getString("file",null)
                    Log.i("Write to preferences",namefile)



                    // TODO: update database else do nothing
                    selectFileButton.text = uri.toString()
                }
            }
        }


    }



    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}