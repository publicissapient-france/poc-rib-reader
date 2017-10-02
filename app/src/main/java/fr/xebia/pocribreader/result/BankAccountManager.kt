package fr.xebia.pocribreader.result

import fr.xebia.pocribreader.validator.IbanValidator

class BankAccountManager {

    val bankAccount: BankAccount = BankAccount("", "", "", "")

    val ibanValidator: IbanValidator = IbanValidator()

    fun checkIban(value: String?): Boolean {
        if (value != null) {
            if (ibanValidator.validateIban(value)) {
                bankAccount.iban = value
                return true
            }
        }
        return false
    }

}