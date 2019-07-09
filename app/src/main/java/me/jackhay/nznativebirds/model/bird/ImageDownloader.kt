package me.jackhay.nznativebirds.model.bird

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.net.URL

class ImageDownloader(private val imageView: ImageView) : AsyncTask<URL, Void, Bitmap>() {
    override fun doInBackground(vararg urls: URL): Bitmap {
        return BitmapFactory.decodeStream(urls[0].openConnection().getInputStream())
    }

    override fun onPostExecute(bitmap: Bitmap) {
        imageView.setImageBitmap((bitmap))
    }
}