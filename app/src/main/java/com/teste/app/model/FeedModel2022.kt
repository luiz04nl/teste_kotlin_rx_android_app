package com.teste.app.model

import org.json.JSONException
import org.json.JSONObject

class FeedModel2022(jsonObject: JSONObject) : FeedModelImpl() {

    companion object {
        private val TYPE = 2022
    }

    override lateinit var publicationDescription: String
    override lateinit var linkUrl: String
    override lateinit var title: String
    override lateinit var author: Author
    override var publicationCountLike: Int = 0
    override var feed_status: Int = 0

    override var type: Int
        get() = TYPE
        set(type) = throw NotImplementedError()

    init {
        try {
            val data = jsonObject.getJSONObject("data")
            this.title = data.getString("publicationTitle")
            this.author = Author(data.getString("publicationAuthorName"))
            this.publicationDescription = data.getString("publicationDescription")
            this.linkUrl = data.getString("linkUrl")
            this.publicationCountLike = data.getInt("publicationCountLike")
            this.feed_status = data.getInt("feed_status")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}


