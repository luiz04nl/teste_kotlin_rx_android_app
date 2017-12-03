package com.teste.app.model

import org.json.JSONException
import org.json.JSONObject

class FeedModel1999(jsonObject: JSONObject) : FeedModelImpl() {

    companion object {
        private val TYPE = 1999
    }

    override lateinit var title: String
    override lateinit var author: Author
    override lateinit var community: Community
    override var feed_status: Int = 0

    override var type: Int
        get() = TYPE
        set(type) = throw NotImplementedError("")

    init {
        try {
            val data = jsonObject.getJSONObject("data")
            this.title = data.getString("title")
            this.author = Author(data.getJSONObject("author"))
            this.community = Community(data.getJSONObject("community"))
            this.feed_status = data.getInt("feed_status")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

}
