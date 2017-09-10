package video.xdownloader.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import video.xdownloader.R;

import static com.bumptech.glide.Priority.IMMEDIATE;


/**
 * Created by jaswinderwadali on 18/08/16.
 */
public class Utils {

    public static final String fitPackageName = "com.google.android.apps.fitness";


//    private static SimpleDraweeView findAndPrepare(SimpleDraweeView view) {
//        view.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
//        return view;
//    }
//
//
//    private static SimpleDraweeView findViewAndSetController(
//            SimpleDraweeView viewId,
//            DraweeController controller, boolean centerCrop) {
//        SimpleDraweeView view = findAndPrepare(viewId);
//        view.getHierarchy().setProgressBarImage(android.R.color.white);
//        view.getHierarchy().setActualImageScaleType(centerCrop ? ScalingUtils.ScaleType.CENTER_CROP : ScalingUtils.ScaleType.FIT_CENTER);
//        view.setController(controller);
//        return view;
//    }
//
//    public static SimpleDraweeView findViewAndLoadAnimatedImageUri(SimpleDraweeView viewId, String uri, boolean centerCrop) {
//        ImageDecodeOptionsBuilder b = new ImageDecodeOptionsBuilder();
//        b.setForceStaticImage(false);
//        ImageDecodeOptions imageDecodeOptions = new ImageDecodeOptions(b);
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri)).setImageDecodeOptions(imageDecodeOptions).setLocalThumbnailPreviewsEnabled(true).build();
//
//        DraweeController animatedController = Fresco.newDraweeControllerBuilder()
//                .setAutoPlayAnimations(true)
//                .setImageRequest(request)
//                .build();
//        return findViewAndSetController(viewId, animatedController, centerCrop);
//    }
//

    public static <T> void findViewAndLoadAnimatedImageUri(ImageView imageView, T uri, boolean centerCrop) {
        if (imageView != null) {
            try {
                RequestManager into = Glide.with(imageView.getContext());
                if (centerCrop) {
                    into.load(uri).priority(IMMEDIATE)
                            .placeholder(R.drawable.thor).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .crossFade().skipMemoryCache(false)
                            .into(imageView);

                } else {
                    into.load(uri).fitCenter().priority(IMMEDIATE)
                            .placeholder(R.drawable.thor).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .crossFade().skipMemoryCache(false)
                            .into(imageView);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static <T> void circleImage(final ImageView imageView, T uri, final boolean border) {
        Glide.with(imageView.getContext()).load(uri).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), border ? addWhiteBorder(resource, imageView.getContext()) : resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static <T> void findViewAndLoadAnimatedImageUriFB(ImageView imageView, T uri, boolean centerCrop) {
        findViewAndLoadAnimatedImageUri(imageView, String.format("https://graph.facebook.com/%s/picture?type=normal", uri), centerCrop);
    }
public static <T> void loadLargeFBimage(ImageView imageView, T uri, boolean centerCrop) {
        findViewAndLoadAnimatedImageUri(imageView, String.format("https://graph.facebook.com/%s/picture?type=large", uri), centerCrop);
    }

    public static <T> void circleImageFb(final ImageView imageView, String uri, final boolean border) {
        Utils.circleImage(imageView, String.format("https://graph.facebook.com/%s/picture?type=normal", uri), true);


    }


    private static Bitmap addWhiteBorder(Bitmap resource, Context context) {
        int w = resource.getWidth();
        int h = resource.getHeight();
        int radius = Math.min(h / 2, w / 2);
        Bitmap output = Bitmap.createBitmap(w + 8, h + 8, Bitmap.Config.ARGB_8888);
        Paint p = new Paint();
        p.setAntiAlias(true);
        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Paint.Style.FILL);
        c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        c.drawBitmap(resource, 4, 4, p);
        p.setXfermode(null);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        p.setStrokeWidth(6);
        c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);
        return output;
    }


    public static boolean hasReadState(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }


    public static boolean hasLocationPermission(Context context) {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }


    public static boolean hasBothPermission(Context context) {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED);
    }


    public static boolean appInstalledOrNot(String uri, PackageManager packageManager) {
        boolean app_installed = false;
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    public static void setDrawerFont(TextView textView, Context context) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "jas.ttf");
        textView.setTypeface(typeFace);
//

    }

    public static void setDrawerFont(TextView textView) {
        Typeface typeFace = Typeface.createFromAsset(textView.getContext().getAssets(), "jas.ttf");
        textView.setTypeface(typeFace);
//
    }


    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    public static List<String> getAllVideoList(Context context) {
        List<String> uriList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Thumbnails.DATA};
        Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
        int vidsCount = 0;
        if (c != null) {
            vidsCount = c.getCount();
            while (c.moveToNext()) {
                Log.d("VIDEO", c.getString(0));
                Log.d("THUMB", c.getString(1));

                uriList.add(c.getString(0));
            }
            c.close();
        }
        return uriList;
    }

}
