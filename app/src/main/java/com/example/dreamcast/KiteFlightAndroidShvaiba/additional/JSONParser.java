package com.example.dreamcast.KiteFlightAndroidShvaiba.additional;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by Dreamcast on 06.10.2016.
 */

public class JSONParser {

    private final DefaultHttpClient httpClient = new DefaultHttpClient();

    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {

        try (final InputStream body = getResponseBody(url, method, params);
             final BufferedReader reader = new BufferedReader(new InputStreamReader(body, "iso-8859-1"), 8)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return new JSONObject(sb.toString());
        } catch (IOException e) {
            Log.e("IO_Error", "Error reading response body: " + e.toString());
        } catch (JSONException e) {
            Log.e("JSON_Parser", "Error parsing data: " + e.toString());
        }
        return null;
    }

    private InputStream getResponseBody(String url, String method, List<NameValuePair> params) throws IOException {
        HttpUriRequest request = null;
        if ("POST".equals(method)) {
            request = new HttpPost(url);
            ((HttpPost)request).setEntity(new UrlEncodedFormEntity(params));
        } else if ("GET".equals(method)) {
            final String paramString = URLEncodedUtils.format(params, "utf-8");
            request = new HttpGet(String.format("%s?%s", url, paramString));
        }
        HttpResponse httpResponse = httpClient.execute(request);
        HttpEntity httpEntity = httpResponse.getEntity();
        return httpEntity.getContent();
    }
}