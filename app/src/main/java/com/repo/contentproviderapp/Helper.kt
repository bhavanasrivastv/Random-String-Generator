package com.repo.contentproviderapp

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.repo.contentproviderapp.Model.RandomTextData
import org.json.JSONObject

fun queryRandomString(context: Context, maxLength: Int ): RandomTextData?{
    val uri = Uri.parse("content://com.iav.contestdataprovider/text")
    val bundle = Bundle().apply{
        putInt(ContentResolver.QUERY_ARG_LIMIT,maxLength)
    }
    try {

        context.contentResolver.query(uri, null, bundle, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex("data")
                if (columnIndex != -1) {
                    val jsonString = cursor.getString(columnIndex)
                    val jsonObject = JSONObject(jsonString).getJSONObject("randomText")
                    return RandomTextData(
                        value = jsonObject.getString("value"),
                        length = jsonObject.getInt("length"),
                        created = jsonObject.getString("created")
                    )
                }
            }
        }
    }catch (e: Exception) {
        Log.e("ContentProviderError", "Query failed: ${e.message}")
        e.printStackTrace()
    }
    return null
}