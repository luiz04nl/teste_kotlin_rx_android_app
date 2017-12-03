package com.teste.app.model

import org.json.JSONException
import org.json.JSONObject

class FeedModel1997(jsonObject: JSONObject) : FeedModelImpl() {

    companion object {
        private val TYPE = 1997
    }

    override lateinit var title: String
    override lateinit var author: Author
    override lateinit var master: Master
    override var feed_status: Int = 0

    override var type: Int
        get() = TYPE
        set(type) = throw NotImplementedError("")

    init {
        try {
            val data = jsonObject.getJSONObject("data")
            this.title = data.getString("title")
            this.author = Author(data.getString("authorName"))
            this.master = Master(data.getString("masterName"))
            this.feed_status = data.getInt("feed_status")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}



