package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.view.View;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class UrlImageParser implements ImageGetter {
    Context c;
    View container;

    /***
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     * @param t
     * @param c
     */
    public UrlImageParser(View t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        source = source.replaceAll("\\\\", "");
//        source = source.substring(1, source.length() - 1);

//        source = "http://gobrainwiz.in/upload/images/1533969401_D1.png";
        LogUtils.e(source);
        final UrlDrawable urlDrawable = new UrlDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);


        // get the actual source
        ImageView view = new ImageView(c);


        /*Glide.with(c)
                .load(source)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//Play with bitmap
                        urlDrawable.drawable = new BitmapDrawable(c.getResources(), resource);
                        UrlImageParser.this.container.invalidate();
                    }
                });*/


       /* final String finalSource = source;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    GlideDrawable glideDrawable = Glide.with(c).load(finalSource).into(100, 100).get();
                    urlDrawable.drawable = glideDrawable;
                    UrlImageParser.this.container.invalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });*/


/*
        Glide.with(c).load(source).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                // set the correct bound according to the result from HTTP call
                urlDrawable.setBounds(0, 0, 0 + resource.getIntrinsicWidth(), 0
                        + resource.getIntrinsicHeight());

                // change the reference of the current drawable to the result
                // from the HTTP call
                urlDrawable.drawable = resource;

                // redraw the image by invalidating the container
                UrlImageParser.this.container.invalidate();
                return false;
            }
        }).into(view);
*/
//        asyncTask.execute(source);


        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        UrlDrawable urlDrawable;

        public ImageGetterAsyncTask(UrlDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            if (result != null) {
                urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0
                        + result.getIntrinsicHeight());

                // change the reference of the current drawable to the result
                // from the HTTP call
                urlDrawable.drawable = result;
            }


            // redraw the image by invalidating the container
            UrlImageParser.this.container.invalidate();
        }

        /***
         * Get the Drawable from URL
         * @param urlString
         * @return
         */
        public Drawable fetchDrawable(String urlString) {


            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");

                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
// Scale it to 50 x 50
                drawable = new BitmapDrawable(c.getResources(), Bitmap.createScaledBitmap(bitmap, 500, 100, true));


                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0
                        + drawable.getIntrinsicHeight());


                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);
            return response.getEntity().getContent();
        }
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        // the drawable that you need to set, you could set the initial drawing
        // with the loading image if you need to
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
}