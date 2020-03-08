package com.example.ledger_companion.dbModels

import android.provider.BaseColumns


object Tables {

    object Accounts : BaseColumns {
        const val NAME = "Accounts"
        const val COLUMN_NAME_Name = "Name"
    }

    object AccountsInTransactions : BaseColumns {
        const val NAME = "AccountsInTransactions"
        const val COLUMN_NAME_TRANSACTION_ID = "TransactionID"
        const val COLUMN_NAME_ACCOUNT_ID = "AccountID"
        const val COLUMN_NAME_COMMODITY = "Commodity"
        const val COLUMN_NAME_VALUE = "Value"
    }

    object Payees : BaseColumns {
        const val NAME = "Payees"
        const val COLUMN_NAME_Name = "Name"
    }

    object Transactions : BaseColumns {
        const val NAME = "Transactions"
        const val COLUMN_NAME_DATE = "Date"
        const val COLUMN_NAME_PAYEE_ID = "PayeeID"
        const val COLUMN_NAME_NOTE = "Note"
        const val COLUMN_NAME_FIRST_LINE = "firstLine"
        const val COLUMN_NAME_LAST_LINE = "lastLine"
    }
}

class Account(val id: Int = 0,
              val name: String = "")

class AccountInTransaction(val id: Int =0,
                           val transactionId:Long = 0,
                           val accountId:Long =0,
                           val commodity: String = "",
                           val value: Float = 0f)
class CompleteAccountInTransaction(val id: Long =0,
                           val transactionId:Long = 0,
                           val account: String ="",
                           val commodity: String = "",
                           val value: Float = 0f)


class Payee(val id: Long = 0,
            val name: String = "")

class Transaction(val id: Long = 0,
                  val date: String = "",
                  val payeeId: Long = 0,
                  val note: String = "",
                  val firstLine: Int = 0,
                  val lastLine: Int = 0)

class CompleteTransaction(val id: Long = 0,
                          val payee: String = "",
                          val note: String = "",
                          val accountsInTransaction: Array<CompleteAccountInTransaction>,
                          val firstLine: Int = 0,
                          val lastLine: Int = 0)