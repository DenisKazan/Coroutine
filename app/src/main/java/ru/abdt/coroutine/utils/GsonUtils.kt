package ru.abdt.coroutine.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

internal object GsonUtils {

    internal fun create(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .registerTypeAdapter(Int::class.java, IntegerDeserializer())
        .registerTypeAdapter(java.lang.Integer::class.java, IntegerDeserializer())
        .registerTypeAdapter(Long::class.java, LongDeserializer())
        .registerTypeAdapter(java.lang.Long::class.java, LongDeserializer())
        .registerTypeAdapter(Float::class.java, FloatDeserializer())
        .registerTypeAdapter(java.lang.Float::class.java, FloatDeserializer())
        .registerTypeAdapter(Double::class.java, DoubleDeserializer())
        .registerTypeAdapter(java.lang.Double::class.java, DoubleDeserializer())
        .create()

    internal class DateDeserializer : JsonDeserializer<Date> {

        private val dateFormatters: MutableList<DateFormat>

        init {
            this.dateFormatters = ArrayList()
            for (i in DATE_FORMATS.indices) {
                try {
                    dateFormatters.add(SimpleDateFormat(DATE_FORMATS[i], Locale.getDefault()))
                } catch (e: Exception) {
                    // no trace // TODO check formats
                }

            }
        }

        @Throws(JsonParseException::class)
        override fun deserialize(jsonElement: JsonElement, typeOF: Type,
                                 context: JsonDeserializationContext
        ): Date {
            return parse(jsonElement.asString)
        }

        @Synchronized
        @Throws(JsonParseException::class)
        fun parse(dateString: String): Date {
            var date: String
            if (dateString.contains("Z")) {
                date = dateString.replace("Z", "+0000")
                if (date.length > 22 && date[22] == ':') {
                    date = date.substring(0, 22) + date.substring(23)  // to get rid of the ":"
                }
            } else {
                date = dateString
            }
            dateFormatters.forEach{ formatter ->

                try {
                    return formatter.parse(date)
                } catch (e: Exception) {
                }

            }

            throw JsonParseException("Unparseable date: \"" + dateString
                    + "\". Supported formats: " + Arrays.toString(DATE_FORMATS))
        }

        companion object {
            val DATE_FORMATS = arrayOf(
                "dd.MM.yyyy HH:mm:ss",
                "dd.MM.yyyy'T'HH:mm:ss",
                "dd.MM.yyyy'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSSZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSSSX",
                "yyyy-MM-dd'T'HH:mm:ss.SSSS'X'",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd", "dd.MM.yyyy")
        }
    }

    internal class IntegerDeserializer : JsonDeserializer<Int> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type, context: JsonDeserializationContext)= when {
            json == null || json.isJsonNull -> null
            !json.isJsonPrimitive -> throw JsonParseException(json.toString() + " is not JsonPrimitive")
            json.asJsonPrimitive.isNumber -> json.asInt
            json.asJsonPrimitive.isString && json.asString.isNotBlank() -> json.asString.toInt()
            json.asJsonPrimitive.isString -> 0
            else -> context.deserialize(json, typeOfT)
        }
    }

    internal class LongDeserializer : JsonDeserializer<Long> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type, context: JsonDeserializationContext)= when {
            json == null || json.isJsonNull -> null
            !json.isJsonPrimitive -> throw JsonParseException(json.toString() + " is not JsonPrimitive")
            json.asJsonPrimitive.isNumber -> json.asLong
            json.asJsonPrimitive.isString && json.asString.isNotBlank() -> json.asString.toLong()
            json.asJsonPrimitive.isString -> 0L
            else -> context.deserialize(json, typeOfT)
        }
    }

    internal class FloatDeserializer : JsonDeserializer<Float> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type, context: JsonDeserializationContext) = when {
            json == null || json.isJsonNull -> null
            !json.isJsonPrimitive -> throw JsonParseException(json.toString() + " is not JsonPrimitive")
            json.asJsonPrimitive.isNumber -> json.asFloat
            json.asJsonPrimitive.isString && json.asString.isNotBlank() -> json.asString.toFloat()
            json.asJsonPrimitive.isString -> 0f
            else -> context.deserialize(json, typeOfT)
        }
    }

    internal class DoubleDeserializer : JsonDeserializer<Double> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type, context: JsonDeserializationContext) = when {
            json == null || json.isJsonNull -> null
            !json.isJsonPrimitive -> throw JsonParseException(json.toString() + " is not JsonPrimitive")
            json.asJsonPrimitive.isNumber -> json.asDouble
            json.asJsonPrimitive.isString && json.asString.isNotBlank() -> json.asString.toDouble()
            json.asJsonPrimitive.isString -> 0.0
            else -> context.deserialize(json, typeOfT)
        }

    }
}
