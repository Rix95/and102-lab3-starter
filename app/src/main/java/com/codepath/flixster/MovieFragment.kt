package com.codepath.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray

private const val API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class MovieFragment : Fragment() {

    /*
     * Constructing the view
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)





        return view



    }






    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        //


        val client = AsyncHttpClient()
        client.get(API_URL, object: JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                progressBar.hide()
                Log.e("bugsad", "Heaaay")
                val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                Log.e("hello green", resultsJSON.toString())
                val moviesRawJSON : String = resultsJSON.toString()
                Log.e("Blue", moviesRawJSON)
                val gson = Gson()
                val arrayBookType = object : TypeToken<List<Movie>>() {}.type

                val models : List<Movie> = gson.fromJson(moviesRawJSON, arrayBookType)
                recyclerView.adapter = MovieRecyclerViewAdapter(models)


            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                progressBar.hide()
                Log.e("Mainbg", "onFailure $statusCode")
            }




        })



//        // Create and set up an AsyncHTTPClient() here
//        val client = AsyncHttpClient()
//        val params = RequestParams()
//        params["api-key"] = API_KEY
//
//        // Using the client, perform the HTTP request
//        client[
//            "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json",
//            params,
//            object : JsonHttpResponseHandler()
//        { //connect these callbacks to your API call
//
//            /*
//             * The onSuccess function gets called when
//             * HTTP response status is "200 OK"
//             */
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Headers,
//                json: JsonHttpResponseHandler.JSON
//                ) {
//                // The wait for a response is over
//                progressBar.hide()
//
//                //TODO - Parse JSON into Models
//                val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
//                val booksRawJSON : String = resultsJSON.get("books").toString()
//                Log.e("Blue", booksRawJSON)
//                val gson = Gson()
//                val arrayBookType = object : TypeToken<List<BestSellerBook>>() {}.type
//
//                val models : List<BestSellerBook> = gson.fromJson(booksRawJSON, arrayBookType)
//                recyclerView.adapter = BestSellerBooksRecyclerViewAdapter(models, this@MovieFragment)
//
//                // Look for this in Logcat:
//                Log.d("MovieFragment", "response successful")
//            }
//
//            /*
//             * The onFailure function gets called when
//             * HTTP response status is "4XX" (eg. 401, 403, 404)
//             */
//            override fun onFailure(
//                statusCode: Int,
//                headers: Headers?,
//                errorResponse: String,
//                t: Throwable?
//            ) {
//                // The wait for a response is over
//                progressBar.hide()
//
//                // If the error is not null, log it!
//                t?.message?.let {
//                    Log.e("MovieFragment", errorResponse)
//                }
//            }
//        }]


    }




}
