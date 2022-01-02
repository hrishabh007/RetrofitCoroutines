package com.app.retrofitcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.app.retrofitcoroutines.api.AlbumService
import com.app.retrofitcoroutines.api.RetrofitInstance
import com.app.retrofitcoroutines.databinding.ActivityMainBinding
import com.app.retrofitcoroutines.model.Album
import com.app.retrofitcoroutines.model.AlbumItem
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService: AlbumService
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)



       // albumResponce()
        uploadAlbum()
        //pathResponce()
    }

    private fun albumResponce() {
        val responseLiveData: LiveData<Response<Album>> = liveData {

            //get
            val response = retService.getAlbums()
            //post
            //val response = retService.getUserAlbum(3)
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumsList = it.body()?.listIterator()
            if (albumsList != null) {
                while (albumsList.hasNext()) {
                    val albumsItem = albumsList.next()
                    val result = " " + "Album Title : ${albumsItem.title}" + "\n" +
                            " " + "Album id : ${albumsItem.id}" + "\n" +
                            " " + "User id : ${albumsItem.userId}" + "\n\n\n"
                    activityMainBinding.textView.append(result)
                }
            }
        })
    }

    private fun pathResponce() {
        val pathResponse: LiveData<Response<AlbumItem>> = liveData {
            val response: Response<AlbumItem> = retService.getAlbum(3)
            emit(response)
        }
        pathResponse.observe(this, Observer {
            val title: String? = it.body()?.title
            Toast.makeText(this, "" + title, Toast.LENGTH_SHORT).show()
        })
    }

    private fun uploadAlbum() {
        val album = AlbumItem(0, "My title", 3)
        val postResponse: LiveData<Response<AlbumItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }
        postResponse.observe(this, Observer {
            val receivedAlbumsItem = it.body()
            val result = " " + "Album Title : ${receivedAlbumsItem?.title}" + "\n" +
                    " " + "Album id : ${receivedAlbumsItem?.id}" + "\n" +
                    " " + "User id : ${receivedAlbumsItem?.userId}" + "\n\n\n"
            activityMainBinding.textView.text = result
        })

    }
}