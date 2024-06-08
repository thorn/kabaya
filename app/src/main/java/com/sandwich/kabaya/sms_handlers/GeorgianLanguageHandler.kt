package com.sandwich.kabaya.sms_handlers
import org.json.JSONObject
// If the message is in Georgian, the handler will return a JSON string with an inline keyboard
// for telegram that contains links to Google Translate and Yandex Translate.
class GeorgianLanguageHandler {
    fun handle(message: String): String? {
        if (!isGeorgian(message)) {
            return null
        }

        val googleTranslateUrl = "https://translate.google.com/?sl=ka&tl=ru&op=translate&text=$message"
        val yandexTranslateUrl = "https://translate.yandex.ru/?source_lang=ka&target_lang=ru&text=$message"
        val tgInlineKeyboard = mapOf(
            "inline_keyboard" to listOf(
                listOf(
                    mapOf(
                        "text" to "Google Translate",
                        "url" to googleTranslateUrl
                    ),
                    mapOf(
                        "text" to "Yandex Translate",
                        "url" to yandexTranslateUrl
                    )
                )
            )
        )
        return JSONObject(tgInlineKeyboard).toString()
    }

    private fun isGeorgian(text: String): Boolean {
        val georgianRegex = Regex("[\u10A0-\u10FF]")
        return georgianRegex.containsMatchIn(text)
    }
}