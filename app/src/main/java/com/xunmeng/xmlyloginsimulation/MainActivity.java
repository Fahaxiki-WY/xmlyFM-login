package com.xunmeng.xmlyloginsimulation;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client;
    private OkHttpClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建一个网络请求
        client = new OkHttpClient()
                .newBuilder()
                // 禁止了重定向
                .followRedirects(false)
                .followSslRedirects(false)
                .build();

        client2 = new OkHttpClient()
                .newBuilder()
                // 禁止了重定向
                .followRedirects(false)
                .followSslRedirects(false)
                .build();
    }

    public void btn(View view) throws Exception {

        // 先向服务器get请求一下，同步

        new Thread() {
            @Override
            public void run() {

                // 准备get请求内容
                Request request = new Request.Builder()
                        .addHeader("Cookie", Constant.Cookie())
                        .addHeader("Cookie2", Constant.COOKIE2)
                        .addHeader("Accept", Constant.ACCEPT)
                        .addHeader("user-agent", Constant.USER_AGENT)
                        .addHeader("Host", Constant.HOST)
//                        .addHeader("Accept-Encoding", Constant.ACCEPT_ENCODING)
                        .addHeader("Connection", Constant.CONNECTION)
                        .url(Constant.GET_URL)
                        .build();


                try {
                    // 发送请求，异步
                    Response response = client.newCall(request).execute();
                    // 拿到返回的内容
                    String result = response.body().string();
                    // Gson提供的Gson对象
                    Gson gson = new Gson();
                    // 转为map
                    Map<String, String> map = new HashMap<String, String>();
                    // 填充数据
                    map = gson.fromJson(result, map.getClass());
                    String token = map.get("token");

                    // 获取到输入的账号密码
                    EditText userText = findViewById(R.id.username);
                    EditText passText = findViewById(R.id.password);
                    String username = userText.getText().toString().trim();
                    String password = passText.getText().toString().trim();

                    // 填充数据
                    map.put("account", username);
                    map.put("nonce", token);
                    map.put("password", AlgorithmUtils.MD5encrypt(password));
                    map.put("signature", Constant.getSignatrue(username, map.get("password"), map.get("token")));

                    String body = Constant.fomat(map);
                    posthttp(body);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // post请求
    private void posthttp(String body) {

//        String str = "bZMlcVC5RjFOP3stc0+7y0B9KAURLvCpzkmWAard7w24JDetTO3v2sWykLrmQGnzAFEokmeCjO8E\n" +
//                "ckjpz5EnL57pOJS9/7h7J/VdTCZvCJg7Ar/3DZ9K3jjOFIMYpa90QYfhUnYJRDeWuGn2pFgDzoRX\n" +
//                "31gICHN1BDz/zDhY9NRHIMsjCFZtIQqJGLUh/3U4kEj5Z3GAygKx/6Iz7E8+8P1yE2Cp/b+v/q3n\n" +
//                "A/lrfdlTPOc9b1REwR8BArMtrWaDLVcsMXJtsv2HXlQfZu5YPqLaCoXc35orsurRchEQQchczDvw\n" +
//                "T/UFW8h6IIg1Y+83sEhISRoL1ByxoSfA+FjTxQ==";

        // 需要请求体,把请求参数放在里面
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), body);

        // 准备post请求内容
        Request postrequest = new Request.Builder()
                .addHeader("Cookie", Constant.Cookie())
                .addHeader("Cookie2", Constant.COOKIE2)
                .addHeader("Accept", Constant.ACCEPT)
                .addHeader("user-agent", Constant.USER_AGENT)
                .addHeader("Content-Type", Constant.CONTENT_TYPE)
                .addHeader("Content-Length", Constant.CONTENT_LENGTH)
                .addHeader("Host", Constant.HOST)
                .addHeader("Connection", Constant.CONNECTION)
//                    .addHeader("Accept-Encoding", Constant.ACCEPT_ENCODING)
                .post(requestBody)
                .url(Constant.POST_URL)
                .build();

        Call call = client2.newCall(postrequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("响应失败");
            }

            @Override
            public void onResponse(Call call, Response resp) throws IOException {
                System.out.println(resp.message());
                System.out.println(resp.headers());
                System.out.println(resp.body().string());
            }
        });
    }


}