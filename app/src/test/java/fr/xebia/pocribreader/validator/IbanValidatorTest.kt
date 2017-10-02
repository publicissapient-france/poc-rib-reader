package fr.xebia.pocribreader.validator

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IbanValidatorTest {

    private val ibanValidator: IbanValidator = IbanValidator()

    @Test
    fun should_iban_be_valid() {
        val validateIban = ibanValidator.validateIban("FR7630056000620062036004802")
        assertTrue(validateIban)
    }

    @Test
    fun should_iban_with_spaces_be_valid() {
        val validateIban = ibanValidator.validateIban("FR76 3005 6000 6200 6203 6004 802")
        assertTrue(validateIban)
    }

    @Test
    fun should_iban_not_be_valid() {
        val validateIban = ibanValidator.validateIban("FAUX RIB")
        assertFalse(validateIban)
    }
}