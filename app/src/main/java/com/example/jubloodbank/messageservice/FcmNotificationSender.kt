import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

object FcmNotificationSender {

    private const val FCM_API_URL = "https://fcm.googleapis.com/fcm/send"
    private const val SERVER_KEY = "AAAA4mfCoQs:APA91bFq2uxEiGeasW0EqNLCvW_Db3CJZwXJjtdVlg314sj8vts5m7wE3aEYOSNXTztLutn_PE14gMvFJFAl_vdPYRCHXD62awGMOrp_s1Nw_i29IzRokD0MBF4dQg_3EGQ6tZQCyrWH" // Replace with your FCM server key

    fun sendPushNotification(deviceToken: String, title: String, message: String) {
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()

        val json = """
            {
                "to": "$deviceToken",
                "notification": {
                    "title": "$title",
                    "body": "$message"
                }
            }
        """.trimIndent()

        val body = RequestBody.create(mediaType, json)
        val request = Request.Builder()
            .url(FCM_API_URL)
            .post(body)
            .addHeader("Authorization", "key=$SERVER_KEY")
            .build()

        try {
            val response: Response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            // Handle the response as needed (e.g., log success or error)
            println("FCM Response: $responseBody")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
