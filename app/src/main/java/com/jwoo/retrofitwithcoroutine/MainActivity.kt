package com.jwoo.retrofitwithcoroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var retrofitService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        // getRequestWithQueryParameters()
        // getRequestWithParameters()
        uploadAlbum()
    }

    private fun getRequestWithQueryParameters() {

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getAlbums()
            // val response = retrofitService.getSortedAlbums(1)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList: MutableListIterator<AlbumsItem>? = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    Log.d("JWOOLOG", "Title: " + albumsItem.title)
                    val result = "Album Id: ${albumsItem.id}\n" +
                            "Album Title: ${albumsItem.title}\n" +
                            "Album User: ${albumsItem.userId}\n\n\n"

                    text_view.append(result)
                }
            }
        })
    }

    private fun getRequestWithParameters() {
        // path parameter sample
        val responseAlbumItemLiveData: LiveData<Response<AlbumsItem>> = liveData {
            val response = retrofitService.getAlbum(1)
            //val response = retrofitService.getSortedAlbums(1)
            emit(response)
        }

        responseAlbumItemLiveData.observe(this, Observer {
            val title: String? = it.body()?.title
            Log.d("JWOOLOG", "Title: " + title)
            val result = "Album Title: $title"
            text_view.append(result)
        })
    }

    private fun uploadAlbum() {
        var album: AlbumsItem = AlbumsItem(0, "ANG ALAMT NI JOHN WOO", 911)

        val postResponse: LiveData<Response<AlbumsItem>> = liveData {
            val response = retrofitService.uploadAlbum(album)
            emit(response)
        }

        postResponse.observe(this, Observer {
            val item: AlbumsItem? = it.body()
            Log.d("JWOOLOG", "Title: " + item!!.title)
            val result = "Album Title: $item"
            text_view.append(result)
        })
    }
}
