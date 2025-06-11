package com.kostryk.icaloryai.data.ai

import com.kostryk.icaloryai.data.BuildKonfig
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.GenerateContentResponse
import dev.shreyaspatil.ai.client.generativeai.type.PlatformImage
import dev.shreyaspatil.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow

class GeminiApi {

    companion object {
        const val PROMPT_GENERATE_UI = "User described: %s" +
                "Here is the main prompt:" +
                "Act as an expert Food Calories Tracker. Your goal is to analyze the provided image of food and provide an estimate of its nutritional content. " +
                "Analyze the image and provide a result with a calorie count, protein, carbs, and fats." +

                "**CRITICAL: Your entire response must be a single line of text. Do not use any newline characters.**" +

                "The response must strictly follow this exact format, with no changes:" +
                "\"Example Name, calories: X, protein: Y, carbs: Z, fats: W\"" +

                "If you are not sure about something, just say 'Error while searching: <error_message>'."
    }

    private val apiKey = BuildKonfig.GEMINI_API_KEY

    private val generativeVisionModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = apiKey
    )

    suspend fun generateContent(prompt: String, imageData: ByteArray?): GenerateContentResponse {
        val content = content {
            imageData?.let { image(PlatformImage(it)) }
            text(PROMPT_GENERATE_UI.replace("%s", prompt))
        }
        return generativeVisionModel.generateContent(content)
    }
}