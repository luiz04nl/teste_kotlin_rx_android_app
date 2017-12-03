package com.teste.app.task

import android.app.ProgressDialog
import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.teste.app.BuildConfig.MOCKY_IO_API_URL
import com.teste.app.request.JsonArrayRequest
import io.reactivex.functions.Consumer
import org.json.JSONArray

class ObterFeedTask(context: Context) : AbstractVolleyTask<JSONArray>(context) {


    private var dialog: ProgressDialog? = null

    /**
     * Método executado antes da requisição http
     *
     * @return
     */
    override fun onPreExecute() {
        this.dialog = ProgressDialog(this.mContext)
        this.dialog!!.setMessage("Aguarde ...")
        this.dialog!!.show()
    }

    /**
     * Método que de fato pede a requisição http
     *
     * @return
     */
    override fun requestData(onNewResults: Consumer<JSONArray>) {
        onPreExecute()

        val requestQueue = Volley.newRequestQueue(this.mContext)
        val body = JSONArray()
        val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET,
                MOCKY_IO_API_URL,
                body,
                { response ->
                    onPostExecute(true)
                    try {
                        val jSONArray: JSONArray = response as JSONArray
                        onNewResults.accept(jSONArray)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                { error -> onPostExecute(false) },
                null)
        requestQueue.add(jsonArrayRequest)
    }

    /**
     * Método executado após a requisição http
     *
     * @return
     */
    override fun onPostExecute(result: Boolean) {
        this.dialog!!.dismiss()
    }

}
