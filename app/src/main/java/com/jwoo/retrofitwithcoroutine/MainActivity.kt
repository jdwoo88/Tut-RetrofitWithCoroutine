package com.jwoo.retrofitwithcoroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var retrofitService : AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        //getRequestWithQueryParameters()
        getRequestWithParameters()
    }

    private fun getRequestWithQueryParameters(){

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retrofitService.getAlbums()
            // val response = retrofitService.getSortedAlbums(1)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumsList: MutableListIterator<AlbumsItem>? = it.body()?.listIterator()
            if (albumsList != null){
                while(albumsList.hasNext()){
                    val albumsItem = albumsList.next();
                    Log.d("JWOOLOG", "Title: " + albumsItem.title)
                    val result =    "Album Id: ${albumsItem.id}\n" +
                                    "Album Title: ${albumsItem.title}\n" +
                                    "Album User: ${albumsItem.userId}\n\n\n"

                    text_view.append(result)
                }
            }
        })
    }

    private fun getRequestWithParameters(){
        // path parameter sample
        val responseAlbumItemLiveData: LiveData<Response<AlbumsItem>> = liveData {
            val response = retrofitService.getAlbum(1)
            //val response = retrofitService.getSortedAlbums(1)
            emit(response)
        }

        responseAlbumItemLiveData.observe(this, Observer {
            val title: String? = it.body()?.title
            Log.d("JWOOLOG", "Title: " + title)
            val result =    "Album Title: $title"
            text_view.append(result)
        })
    }
}
