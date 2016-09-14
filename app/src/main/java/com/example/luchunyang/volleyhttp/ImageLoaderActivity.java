package com.example.luchunyang.volleyhttp;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

/**
 * 如果你觉得ImageRequest已经非常好用了，那我只能说你太容易满足了 ^_^。实际上，Volley在请求网络图片方面可以做到的还远远不止这些，
 * 而ImageLoader就是一个很好的例子。ImageLoader也可以用于加载网络上的图片，并且它的内部也是使用ImageRequest来实现的，
 * 不过ImageLoader明显要比ImageRequest更加高效，因为它不仅可以帮我们对图片进行缓存，还可以过滤掉重复的链接，避免重复发送请求。
 *
 * 1. 创建一个RequestQueue对象。
 * 2. 创建一个ImageLoader对象。
 * 3. 获取一个ImageListener对象。
 * 4. 调用ImageLoader的get()方法加载网络上的图片。
 */
public class ImageLoaderActivity extends AppCompatActivity {

    private ImageView iv;
    private ImageLoader imageLoader;
    private String MEINV = "http://d.hiphotos.baidu.com/image/pic/item/d0c8a786c9177f3e3ca2960c72cf3bc79f3d5618.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        iv = (ImageView) findViewById(R.id.iv);

        //空的cache
//        imageLoader = new ImageLoader(MyApplication.getQueue(), new ImageLoader.ImageCache() {
//            @Override
//            public Bitmap getBitmap(String url) {
//                return null;
//            }
//
//            @Override
//            public void putBitmap(String url, Bitmap bitmap) {
//
//            }
//        });

        imageLoader = new ImageLoader(MyApplication.getQueue(),new BitmapCache());
    }

    public void requestImage(View view) {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv,R.drawable.image_default,R.drawable.image404);
        imageLoader.get(MEINV,imageListener);

    }
}

class BitmapCache implements ImageLoader.ImageCache{

    private LruCache<String,Bitmap> lruCache;

    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024;
        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        lruCache.put(url,bitmap);
    }
}
