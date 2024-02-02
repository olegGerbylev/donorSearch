package donor.utils

import com.fasterxml.jackson.databind.node.ObjectNode
import donor.utils.JSON
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.postgresql.util.PGobject
import java.io.IOException

@Converter(autoApply = true)
class JsonConverter : AttributeConverter<ObjectNode, Any> {
    override fun convertToDatabaseColumn(objectValue: ObjectNode): Any {
        return try {
            val out = PGobject()
            out.type = "json"
            out.value = objectValue.toString()
            out
        } catch (e: Exception) {
            throw IllegalArgumentException("Unable to serialize to json field ", e)
        }
    }

    override fun convertToEntityAttribute(dataValue: Any): ObjectNode {
        return try {
            if (dataValue is PGobject && dataValue.type == "json") {
                JSON.parse(dataValue.value ?: "") as ObjectNode
            } else JSON.objectNode()
        } catch (e: IOException) {
            throw IllegalArgumentException("Unable to deserialize to json field ", e)
        }
    }
}