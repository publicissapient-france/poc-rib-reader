package fr.xebia.pocribreader.result

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.places.AutocompletePrediction
import fr.xebia.pocribreader.R
import fr.xebia.pocribreader.address.AddressValidationManager
import kotlinx.android.synthetic.main.bank_account_info_activity.*

class BankAccountInfoActivity : AppCompatActivity() {

    companion object {
        val BANK_ACCOUNT_PARAM = "BANK_ACCOUNT_PARAM"
    }

    private lateinit var bankAccount: BankAccount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_account_info_activity)

        bankAccount = intent.getParcelableExtra<BankAccount>(BANK_ACCOUNT_PARAM)

        bankAccountInfoIban.text = bankAccount.iban
        bankAccountInfoAddress.text = bankAccount.address
        bankAccountInfoBic.text = bankAccount.bic
    }
}
