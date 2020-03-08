package com.example.ledger_companion.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.ledger_companion.dbModels.CompleteTransaction
import com.example.ledger_companion.dbModels.Payee
import com.example.ledger_companion.dbModels.Tables
import com.example.ledger_companion.dbModels.Transaction

class dbHandler(context: Context) :
    SQLiteOpenHelper(context,DB_NAME,null,DB_VERSION){

    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_ACCOUNTS_TABLE = "CREATE TABLE ${Tables.Accounts.NAME}" +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Tables.Accounts.COLUMN_NAME_Name} text unique)"

        val CREATE_ACCOUNTS_TRANSACTIONS_TABLE = "CREATE TABLE ${Tables.AccountsInTransactions.NAME}" +
                "(${BaseColumns._ID} integer primary key," +
                "${Tables.AccountsInTransactions.COLUMN_NAME_TRANSACTION_ID} integer KEY, " +
                "${Tables.AccountsInTransactions.COLUMN_NAME_ACCOUNT_ID} INTEGER KEY," +
                "${Tables.AccountsInTransactions.COLUMN_NAME_COMMODITY} text," +
                "${Tables.AccountsInTransactions.COLUMN_NAME_VALUE} real)"

        val CREATE_PAYEES_TABLE = "CREATE TABLE ${Tables.Payees.NAME}" +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${Tables.Payees.COLUMN_NAME_Name} text unique)"

        val CREATE_TRANSACTIONS_TABLE = "CREATE TABLE ${Tables.Transactions.NAME}" +
                "(${BaseColumns._ID} Integer PRIMARY KEY," +
                "${Tables.Transactions.COLUMN_NAME_DATE} String," +
                "${Tables.Transactions.COLUMN_NAME_PAYEE_ID} Integer KEY," +
                "${Tables.Transactions.COLUMN_NAME_NOTE} TEXT," +
                "${Tables.Transactions.COLUMN_NAME_FIRST_LINE} Integer unique," +
                "${Tables.Transactions.COLUMN_NAME_LAST_LINE} Integer unique)"

        db.execSQL(CREATE_ACCOUNTS_TABLE)
        db.execSQL(CREATE_ACCOUNTS_TRANSACTIONS_TABLE)
        db.execSQL(CREATE_PAYEES_TABLE)
        db.execSQL(CREATE_TRANSACTIONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DELETE_ACCOUNTS_TABLE = "DROP TABLE IF EXISTS ${Tables.Accounts.NAME}"
        val DELETE_ACCOUNTS_TRANSACTIONS_TABLE = "DROP TABLE IF EXISTS ${Tables.AccountsInTransactions.NAME}"
        val DELETE_PAYEES_TABLE = "DROP TABLE IF EXISTS ${Tables.Payees.NAME}"
        val DELETE_TRANSACTIONS_TABLE = "DROP TABLE IF EXISTS ${Tables.Transactions.NAME}"

        db.execSQL(DELETE_ACCOUNTS_TABLE)
        db.execSQL(DELETE_ACCOUNTS_TRANSACTIONS_TABLE)
        db.execSQL(DELETE_PAYEES_TABLE)
        db.execSQL(DELETE_TRANSACTIONS_TABLE)
        onCreate(db)

    }

//    fun getTransactionById(db: SQLiteDatabase,id:Int): CompleteTransaction {
//        val GET_TRANSACTION = "DROP TABLE IF EXISTS ${Tables.Transactions.NAME}"
//
//        val itemIds = mutableListOf<Long>()
//        val transaction : CompleteTransaction = CompleteTransaction()
//        return transaction
//    }

    fun getAllTransactions(db: SQLiteDatabase){

    }

    fun getAllAccounts(db: SQLiteDatabase): MutableList<String> {
        var accounts = mutableListOf<String>()
        val projection = arrayOf(Tables.Accounts.COLUMN_NAME_Name)

        val cursor = db.query(
            Tables.Accounts.NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        with(cursor) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(BaseColumns._ID))
                accounts.add(itemId)
            }
        }
        return accounts
    }

    fun getAllPayees(db: SQLiteDatabase): MutableList<String> {
        Log.i("getAllPayees","getting payee")
        var payees = mutableListOf<String>()
        val projection = arrayOf(Tables.Payees.COLUMN_NAME_Name)

        val cursor = db.query(
            Tables.Payees.NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        with(cursor) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(Tables.Payees.COLUMN_NAME_Name))
                payees.add(itemId)
            }
        }
        return payees
    }


    fun AddPayee(db: SQLiteDatabase,payee: Payee): Long {
        Log.i("AddPayee","adding payee")
        val payees = getAllPayees(db)
        if  (payees.indexOf(payee.name)==-1) {
            // Create a new map of values, where column names are the keys
            val values = ContentValues().apply {
                put(Tables.Payees.COLUMN_NAME_Name, payee.name)
            }

            // Insert the new row, returning the primary key value of the new row
            return db.insert(Tables.Payees.NAME, null, values)
        }
        return -1
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        private val DB_NAME = "LedgerCompanion.db"
        private val DB_VERSION = 1;
    }

}