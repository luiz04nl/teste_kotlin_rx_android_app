package com.teste.app.task

import android.content.Context
import io.reactivex.functions.Consumer

abstract class AbstractVolleyTask<T>(val mContext: Context) {

    protected abstract fun onPreExecute()

    abstract fun requestData(onNewResults: Consumer<T>)

    protected abstract fun onPostExecute(result: Boolean)

}
