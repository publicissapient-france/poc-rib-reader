package fr.xebia.pocribreader.validator

import org.apache.commons.validator.routines.IBANValidator


class IbanValidator {

    fun validateIban(iban: String): Boolean {
        val trimmedIban = iban.replace("\\s".toRegex(), "")
        return IBANValidator.getInstance().isValid(trimmedIban)
    }
}