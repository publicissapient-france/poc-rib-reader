package fr.xebia.pocribreader.result

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.xebia.pocribreader.R

class BankAccountInfoActivity : AppCompatActivity() {

    companion object {
        val BANK_ACCOUNT_PARAM = "BANK_ACCOUNT_PARAM"
    }

    private lateinit var bankAccount: BankAccount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_account_info_activity)
        bankAccount = intent.getParcelableExtra<BankAccount>(BANK_ACCOUNT_PARAM)
    }
}
