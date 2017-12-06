package wetalk.software.bupt.com.wetalk.model.po;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import wetalk.software.bupt.com.wetalk.listener.DeleteListener;
import wetalk.software.bupt.com.wetalk.listener.ThumbnailUrlListener;
import wetalk.software.bupt.com.wetalk.listener.UploadFileListener;

/**
 * Created by zhangjie on 2017/12/6.
 */

public class WeTalkFile implements Serializable{
    private static final long serialVersionUID = -9145726747813570773L;
    static int maxWeTalkFileSize = 10485760;
    static int maxWeTalkFileSize_block = 209715200;
    static File file;
    private String filename = null;
    private String group = null;
    protected String url = null;
    private String __type = "File";
    //static ImageLoader mImageLoader;
    static int cacheSize;

    public WeTalkFile() {
    }

    public WeTalkFile(File file) {
        WeTalkFile.file = file;
    }

    public WeTalkFile(String fileName, String group, String url) {
        this.filename = fileName;
        this.group = group;
        this.url = url;
    }

    public void upload(final Context context, final UploadFileListener listener) {
        if(file.length() > (long)maxWeTalkFileSize_block) {
            listener.postOnFailure(9007, "WeTalkFile File size must be less than 200M.");
        } else if(!file.exists()) {
            listener.postOnFailure(9008, "WeTalkFile File does not exist.");
        } else {
            (new Thread() {
                public final void run() {
                    (new Though(context, WeTalkFile.file, new XUploadListener() {
                        public final void onSuccess(V data) {
                            this.aG.aE.url = data.bX().ah("file").af("url").getAsString();
                            this.aG.aE.filename = data.bX().ah("file").af("filename").getAsString();
                            this.aG.aE.group = data.bX().ah("file").af("group").getAsString();
                            listener.onSuccess();
                        }

                        public final void onFailure(int code, String e) {
                            listener.onFailure(code, e);
                        }

                        public final void onProgress(Integer value) {
                            listener.onProgress(value);
                        }

                        public final void onStart() {
                            listener.onStart();
                        }

                        public final void onFinish() {
                            listener.onFinish();
                        }
                    })).i();
                }
            }).start();
        }
    }

    public void uploadblock(final Context context, final UploadFileListener listener) {
        /*if(file.length() > (long)maxWeTalkFileSize_block) {
            listener.postOnFailure(9007, "WeTalkFile File size must be less than 200M.");
        } else if(!file.exists()) {
            listener.postOnFailure(9008, "WeTalkFile File does not exist.");
        } else {
            (new Thread() {
                public final void run() {
                    (new Though(context, WeTalkFile.file, new XUploadListener() {
                        public final void onSuccess(V data) {
                            this.aH.aE.url = data.bX().ah("file").af("url").getAsString();
                            this.aH.aE.filename = data.bX().ah("file").af("filename").getAsString();
                            this.aH.aE.group = data.bX().ah("file").af("group").getAsString();
                            listener.onSuccess();
                        }

                        public final void onFailure(int code, String e) {
                            listener.onFailure(code, e);
                        }

                        public final void onProgress(Integer value) {
                            listener.onProgress(value);
                        }

                        public final void onStart() {
                            listener.onStart();
                        }

                        public final void onFinish() {
                            listener.onFinish();
                        }
                    })).i();
                }
            }).start();
        }*/
    }

    public void delete(Context context) {
        this.delete(context, (DeleteListener)null);
    }

    public void delete(Context context, final DeleteListener listener) {
        JSONObject var3 = new JSONObject();
        JSONObject var4 = new JSONObject();

        try {
            var4.put("filename", this.getUrl());
            var3.put("data", var4);
        } catch (JSONException var5) {
            var5.printStackTrace();
        }

        HashMap var7;
        (var7 = new HashMap()).put("params", var3);
        /*This var6 = new This(context, 1, "api", "/8/delfile", var7);
        of.I(context).V(var6, new XListener() {
            public final void onSuccess(V data) {
                if(listener != null) {
                    listener.onSuccess();
                }

            }

            public final void onFailure(int code, String e) {
                if(listener != null) {
                    listener.onFailure(code, e);
                }

            }

            public final void onStart() {
                if(listener != null) {
                    listener.onStart();
                }

            }

            public final void onFinish() {
                if(listener != null) {
                    listener.onFinish();
                }

            }
        });*/
    }

    public void getThumbnailUrl(Context context, int width, int height, ThumbnailUrlListener listener) {
        this.getThumbnailUrl(context, width, height, 80, listener);
    }

    public void getThumbnailUrl(final Context context, int width, int height, int quality, final ThumbnailUrlListener listener) {
        JSONObject var6 = new JSONObject();

        try {
            JSONObject var7;
            (var7 = new JSONObject()).put("image", this.getFileUrl(context));
            var7.put("mode", 5);
            var7.put("quality", quality);
            var7.put("width", width);
            var7.put("height", height);
            var6.put("data", var7);
            var6.put("c", "Fack");
        } catch (JSONException var8) {
            var8.printStackTrace();
        }

        HashMap var10;
        (var10 = new HashMap()).put("params", var6);
        /*This width1 = new This(context, 1, "api", "/8/thumbnail", var10);
        of.I(context).V(width1, new XListener() {
            public final void onSuccess(V data) {
                this.aE.url = data.bX().af("url").getAsString();
                this.aE.filename = data.bX().af("filename").getAsString();
                this.aE.group = data.bX().af("group").getAsString();
                listener.onSuccess(this.aE.getFileUrl(context));
            }

            public final void onFailure(int code, String e) {
                listener.onFailure(code, e);
            }
        });*/
    }

    public void loadImageThumbnail(Context context, ImageView imageView, int width, int height) {
        this.loadImageThumbnail(context, imageView, width, height, 50);
    }

    public void loadImageThumbnail(Context context, final ImageView imageView, int width, int height, int quality) {
        JSONObject var6 = new JSONObject();

        try {
            JSONObject var7;
            (var7 = new JSONObject()).put("image", this.getFileUrl(context));
            var7.put("mode", 5);
            var7.put("quality", quality);
            var7.put("width", width);
            var7.put("height", height);
            var7.put("outType", 1);
            var6.put("data", var7);
            var6.put("c", "Fack");
        } catch (JSONException var8) {
            var8.printStackTrace();
        }

        HashMap var10;
        (var10 = new HashMap()).put("params", var6);
        /*This width1 = new This(context, 1, "api", "/8/thumbnail", var10);
        of.I(context).V(width1, new XListener() {
            public final void onSuccess(V data) {
                byte[] data1;
                Bitmap data2 = BitmapFactory.decodeByteArray(data1 = Base64.decode(data.bX().af("file").getAsString(), 0), 0, data1.length);
                imageView.setImageBitmap(data2);
            }

            public final void onFailure(int code, String e) {
            }
        });*/
    }

    public void loadImage(Context context, final ImageView imageView) {
        /*int var3 = ((ActivityManager)context.getSystemService("activity")).getMemoryClass();
        cacheSize = var3 * 1048576 / 8;
        (mImageLoader = new ImageLoader(of.I(context).Z(context), new cn.bmob.v3.util.This(cacheSize))).get(this.getFileUrl(context), new ImageListener() {
            public final void onErrorResponse(VolleyError error) {
            }

            public final void onResponse(ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }
        });*/
    }

    public void loadImage(Context context, final ImageView imageView, int maxWidth, int maxHeight) {
        int var5 = ((ActivityManager)context.getSystemService("activity")).getMemoryClass();
        cacheSize = var5 * 1048576 / 8;
        /*(mImageLoader = new ImageLoader(of.I(context).Z(context), new cn.bmob.v3.util.This(cacheSize))).get(this.getFileUrl(context), new ImageListener() {
            public final void onErrorResponse(VolleyError error) {
            }

            public final void onResponse(ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());
            }
        }, maxWidth, maxHeight);*/
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGroup() {
        return this.group;
    }

    protected void setGroup(String group) {
        this.group = group;
    }

    public String getFilename() {
        return this.filename;
    }

    protected void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileUrl(Context context) {
        String context1="";
        /*if(this.url.contains("http://")) {
            context1 = this.url;
        } else {
            context1 = (new i(context)).getValue("file", "http://file.bmob.cn");
            context1 = context1 + "/" + this.url;
        }*/

        return context1;
    }

    public static WeTalkFile createEmptyFile() {
        WeTalkFile var0;
        (var0 = new WeTalkFile(new File(""))).setFilename("test.apk");
        return var0;
    }
}
