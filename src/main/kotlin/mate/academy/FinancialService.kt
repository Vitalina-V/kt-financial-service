package mate.academy

const val EUR_CURRENCY_RATE = 0.93
const val GBP_CURRENCY_RATE = 0.82
const val ACCOUNT_NUMBER_LENGTH = 10
const val CURRENCY_CODE_LENGTH = 3
class FinancialService {
    fun transferFunds(
        source: AccountNumber,
        destination: AccountNumber,
        amount: CurrencyAmount,
        currencyCode: CurrencyCode,
        transactionId: TransactionId
    ) : String {
        return "Transferred ${amount.amount} ${currencyCode.code} from ${source.number} " +
                "to ${destination.number}. Transaction ID: ${transactionId.id}"
    }

    fun convertCurrency(
        amount: CurrencyAmount,
        fromCurrency: CurrencyCode,
        toCurrency: CurrencyCode
    ): CurrencyAmount {
        val exchangeRate = getExchangeRate(fromCurrency, toCurrency)
        return CurrencyAmount(amount.amount.times(exchangeRate))
    }

    private fun getExchangeRate(fromCurrency: CurrencyCode, toCurrency: CurrencyCode): Double {
        // Placeholder exchange rate - in a real application, you'd fetch this from a financial API
        return when {
            fromCurrency.code == "USD" && toCurrency.code == "EUR" -> EUR_CURRENCY_RATE
            fromCurrency.code == "USD" && toCurrency.code == "GBP" -> GBP_CURRENCY_RATE
            else -> 1.0
        }
    }
}

@JvmInline
value class AccountNumber(val number: String) {
    init {
        require(number.length == ACCOUNT_NUMBER_LENGTH && number.all { it.isDigit() }) {
            "Invalid account number length"
        }
    }
}

@JvmInline
value class CurrencyAmount(val amount: Double) {
    init {
        require(amount >= 0) {
            "Invalid amount"
        }
    }
}

@JvmInline
value class CurrencyCode(val code: String) {
    init {
        require(
            code.length == CURRENCY_CODE_LENGTH &&
                code.all { it.isLetter() } &&
                code == code.uppercase()) {
            "Invalid currency code"
        }
    }
}

@JvmInline
value class TransactionId(val id: String) {
    init {
        require(id.isNotEmpty()) {
            "Invalid transactionId"
        }
    }
}
