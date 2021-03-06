package com.teste.app.model

import org.json.JSONException
import org.json.JSONObject

class Author {

    var name: String? = null
    var id: Int = 0

    constructor(name: String, id: Int) {
        this.name = name
        this.id = id
    }

    internal constructor(name: String) {
        this.name = name
    }

    internal constructor(jsonObject: JSONObject) {
        try {
            this.name = jsonObject.getString("name")
            this.id = jsonObject.getInt("id")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}
