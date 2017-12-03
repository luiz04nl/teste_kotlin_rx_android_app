package com.teste.app.request

import org.json.JSONArray
import java.util.*

class JsonArrayRequest(method: Int, url: String, jsonRequest: JSONArray,
                       listener: (Any) -> Unit, errorListener: (Any) -> Unit,
                       private var mHearderParams: Map<String, String>?)
    : com.android.volley.toolbox.JsonArrayRequest(method, url, jsonRequest, listener, errorListener) {

    /**
     * Método que sobrescreve o hetHeaders padrao adicionando parametros
     * adicionais no cabeçalho da requisição http
     *
     * @return Map<String, String>
     */
    override fun getHeaders(): Map<String, String> {
        if (this.mHearderParams == null) {
            val hearder = HashMap<String, String>()
            hearder.put("Content-Type", "application/json")
            this.mHearderParams = hearder
        }
        return this.mHearderParams!!
    }

}