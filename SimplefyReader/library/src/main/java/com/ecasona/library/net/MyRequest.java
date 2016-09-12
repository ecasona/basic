package com.ecasona.library.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.listener.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.util.ByteArrayBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * Created by aiy on 2016/9/6.
 * <p>
 * des:
 */

public abstract class MyRequest extends StringRequest {
    private Map map;

    public MyRequest(int mode, String url, Map map, Listener<String> listener) {
        super(mode, url + addParams(mode, map), listener);
        this.map = map;
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }

    /**
     * 请求头参数处理
     *
     * @return 处理过的请求头
     */
    public abstract Map<String, String> addHeaders();

    /**
     * 从响应头中获取需要的信息
     *
     * @param header 响应头
     */
    public abstract void parseHeader(String header);

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(readResponseData(response),
                    HttpHeaderParser.parseCharset(response.headers));
            String mHeader = response.headers.toString();
            parseHeader(mHeader);
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    public static String addParams(int mode, Map<String, String> map) {
        if (Request.Method.POST == mode) {
            return "";
        }

        if (null == map || map.size() == 0) {
            return "";
        }

        Set<String> set = map.keySet();

        String queryString = "?";
        try {
            for (String key : set) {
                String value = (String) map.get(key);
                queryString += key + "=" + URLEncoder.encode(value, "utf-8")
                        + "&";
            }
            // 去掉最后一个空格
            // queryString = queryString.substring(0,queryString.length() - 1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return queryString;
    }

    @Override
    public Map<String, String> getHeaders() {
        return addHeaders();
    }

    private byte[] readResponseData(NetworkResponse response) {
        byte[] repData = response.data;
        String contentEncoding = response.headers.get("Content-Encoding");
        if (contentEncoding != null && contentEncoding.equals("gzip")) {
            try {
                GZIPInputStream gzipIn = new GZIPInputStream(
                        new ByteArrayInputStream(repData));
                try {
                    int i = 4096;
                    ByteArrayBuffer buffer = new ByteArrayBuffer(i);
                    byte[] tmp = new byte[4096];

                    int l;
                    while ((l = gzipIn.read(tmp)) != -1) {
                        buffer.append(tmp, 0, l);
                    }
                    repData = buffer.toByteArray();
                } finally {
                    gzipIn.close();
                }
            } catch (IOException e) {
            }
        }
        return repData;
    }


}
