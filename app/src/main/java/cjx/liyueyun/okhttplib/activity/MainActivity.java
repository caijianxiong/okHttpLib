package cjx.liyueyun.okhttplib.activity;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cjx.okhttp.OkHttpUtils;
import com.cjx.okhttp.callback.BitmapCallback;
import com.cjx.okhttp.callback.ClassFormatCallBack;
import com.cjx.okhttp.callback.FileCallBack;
import com.cjx.okhttp.callback.StringCallback;
import com.cjx.okhttp.cookie.CookieJarImpl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cjx.liyueyun.okhttplib.R;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private String mBaseUrl = "http://mjxc.yun2win.com/";
    private String url = mBaseUrl;

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {//request tag
                case 100:
                    break;
                case 101:
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * get请求
     */
    private void get() {
        OkHttpUtils.get().url(url).id(222).addParams("key", "45645").build().execute(new ClassFormatCallBack<String>() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
            }
        });
    }

    /**
     * post  上传String参数
     */
    private void postJsonString() {
        String url = mBaseUrl + "user!postString";
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("jsonString")//可实体类转成json
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 表单post上传参数
     */
    public void postFrom() {
        OkHttpUtils
                .postFrom()
                .url(url)
                .addHeader("header", "what")
                .addParams("username", "aaa")
                .build()
                .execute(new ClassFormatCallBack<String>() {//string 可为实体类型
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                    }
                });
    }


    /**
     * 文件上传
     */
    private void postFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "img.png");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = mBaseUrl + "user!postFile";
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 网络图片转bitmap
     */
    public void getImage() {
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()
                .url(url)
                .tag(this)
                .build()
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        Log.e("TAG", "onResponse：complete");
//                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * 表单--文件上传
     *
     * @param view
     */
    public void uploadFile(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "img.png");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "name");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");

        OkHttpUtils.postFrom()//
                .addFile("mFile", "img.png", file)//
                .url(url)
                .params(params)
                .headers(headers)
                .build()
                .execute(new MyStringCallback());
    }


    /**
     * 表单--多文件上传
     *
     * @param view
     */
    public void multiFileUpload(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "img.png");
        File file2 = new File(Environment.getExternalStorageDirectory(), "test1#.txt");
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "name");
        params.put("password", "123");

        OkHttpUtils.postFrom()//
                .addFile("mFile", "img.png", file)//
                .addFile("mFile", "test1.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    /**
     * 文件下载
     *
     * @param view
     */
    public void downloadFile(View view) {
        String url = "https://github.com/hongyangAndroid/okhttp-utils/blob/master/okhttputils-2_4_1.jar?raw=true";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "gson-2.2.1.jar") {

                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
//                        mProgressBar.setProgress((int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(File file, int id) {
                    }
                });
    }

    /**
     * 接口字段的增删改查
     *
     * @param view
     */
    public void otherRequestDemo(View view) {
        //also can use delete ,head , patch
        OkHttpUtils
                .put()
                .url("http://11111.com")
                .requestBody
                        ("may be something")
                .build()
                .execute(new MyStringCallback());

        OkHttpUtils
                .head()
                .url(url)
                .addParams("name", "name")
                .build()
                .execute(new MyStringCallback());
    }


    public void clearSession(View view) {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl) {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
    }
}
