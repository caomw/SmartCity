package com.dc.smartcity.base;

/**
 * ͼƬ���ع����࣬�ϲ�ҵ����²�ͼƬ���������м��
 */

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.android.dcone.ut.picasso.Callback;
import com.android.dcone.ut.picasso.Picasso;
import com.android.dcone.ut.picasso.Target;
import com.dc.smartcity.R;

import java.io.File;


public class ImageLoader {

    public static final int STUB_NULL = -1;// ������Ĭ��ͼ

    private static final int STUB_ID = R.drawable.bg_default_common; // Ĭ��ͼƬ���ٲ�����Ĭ��ͼ��������������
    private static final Config DEFAULT_CONFIG = Config.RGB_565;

    private static ImageLoader imageLoader = null;
    private Context context;

    private ImageLoader(Context context) {
        this.context = context;
    }

    public static synchronized ImageLoader getInstance() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(BaseApplication.getInstance());
        }
        return imageLoader;
    }

    /**
     * Ĭ��ͼƬ����
     */
    public void displayImage(String imageUrl, ImageView imageView) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, DEFAULT_CONFIG);
    }

    /**
     * ��Ĭ��ͼƬ��ͼƬ���أ����سɹ�ǰ�ͼ���ʧ�ܺ��ͼƬһ����
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id) {
        if (stub_id == STUB_NULL) {
            Picasso.with(context).load(imageUrl).config(DEFAULT_CONFIG).into(imageView);
        } else {
            displayImage(imageUrl, imageView, stub_id, stub_id, DEFAULT_CONFIG);
        }
    }

    /**
     * �����趨ͼƬƷ�ʵĽӿ�
     *
     * @param imageUrl
     * @param imageView
     * @param config
     */
    public void displayImage(String imageUrl, ImageView imageView, Config config) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, config);
    }

    public void displayImage(String imageUrl, ImageView imageView, int stub_id, Config config) {
        displayImage(imageUrl, imageView, stub_id, stub_id, config);
    }

    /**
     * Ϊ����ҳ���Ƶģ�û�ж�����ͼƬ���ء���Ĭ���������ͼƬ����ʱ���ж�����
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     * @param isNoFade
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id, boolean isNoFade) {
        if (imageUrl == null || "".equals(imageUrl)) {
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(stub_id);
            return;
        }
        if (stub_id == STUB_NULL) {
            Picasso.with(context).load(imageUrl).config(DEFAULT_CONFIG).noFade().into(imageView);
            return;
        }
        if (isNoFade) {
            Picasso.with(context).load(imageUrl).placeholder(stub_id).error(stub_id).config(DEFAULT_CONFIG).noFade().into(imageView);
            return;
        }
        Picasso.with(context).load(imageUrl).placeholder(stub_id).error(stub_id).config(DEFAULT_CONFIG).into(imageView);
    }

    /**
     * ��Ĭ��ͼƬ��ͼƬ���أ����سɹ�ǰ�ͼ���ʧ�ܺ��ͼƬ��ʾ��
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     * @param stub_id_no_img
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id, int stub_id_no_img, Config config) {
        if (imageUrl == null || "".equals(imageUrl)) {
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(stub_id);
            return;
        }
        Picasso.with(context).load(imageUrl).placeholder(stub_id).error(stub_id_no_img).config(config).into(imageView);
    }

    /**
     * ���ص���ͼƬ���أ����سɹ���ʧ�ܡ����ȵĻص���
     */
    public void displayImage(String imageUrl, ImageView imageView, Callback callback) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, callback, DEFAULT_CONFIG);
    }

    public void displayImage(String imageUrl, ImageView imageView, Callback callback, int stub_id) {
        displayImage(imageUrl, imageView, stub_id, stub_id, callback, DEFAULT_CONFIG);
    }

    public void displayImage(String imageUrl, ImageView imageView, Callback callback, Config config) {
        displayImage(imageUrl, imageView, STUB_ID, STUB_ID, callback, config);
    }

    public void displayImage(String imageUrl, ImageView imageView, Callback callback, int stub_id, Config config) {
        displayImage(imageUrl, imageView, stub_id, stub_id, callback, config);
    }

    /**
     * ��Ĭ��ͼƬ�ͻص������ļ���
     *
     * @param imageUrl
     * @param imageView
     * @param stub_id
     * @param stub_id_no_img
     * @param callback
     */
    public void displayImage(String imageUrl, ImageView imageView, int stub_id, int stub_id_no_img, Callback callback, Config config) {
        if (imageUrl == null || "".equals(imageUrl)) {
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setImageResource(stub_id);
            return;
        }
        Picasso.with(context).load(imageUrl).placeholder(stub_id).error(stub_id_no_img).config(config).into(imageView, callback);

    }

    public void displayImageFileFitView(File file, ImageView target) {
        if (file == null) {
            return;
        }
        Picasso.with(context).load(file).fit().centerInside().into(target);
    }

    public void fetch(String imageUrl) {
        if (imageUrl == null || "".equals(imageUrl))
            return;
        Picasso.with(context).load(imageUrl).fit();
    }

    public void fetchToTarget(String imageUrl, Target target) {
        if (imageUrl == null || "".equals(imageUrl))
            return;
        Picasso.with(context).load(imageUrl).into(target);
    }

    public void fetchToTarget(String imageUrl, Target target, int res) {
        if (imageUrl == null || "".equals(imageUrl))
            return;
        Picasso.with(context).load(imageUrl).placeholder(res).into(target);
    }

    /**
     * ȡ�����������ͷ���Դ��
     *
     * @param view
     */
    public void cancelRequest(ImageView view) {
        Picasso.with(context).cancelRequest(view);
    }

}
