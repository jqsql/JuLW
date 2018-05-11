package com.liao310.www.net.https;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.xutils.http.body.RequestBody;

import android.net.Uri;
import android.text.TextUtils;

public class UrlEncodedParamsBody implements RequestBody {  
  
    private byte[] content;  
    private String charset = "UTF-8";  
  
    public UrlEncodedParamsBody(Map<String,String> map, String charset) throws IOException {  
        if (!TextUtils.isEmpty(charset)) {  
            this.charset = charset;  
        }  
        StringBuilder contentSb = new StringBuilder();  
        if(null!=map){  
            for(Map.Entry<String, String> entry : map.entrySet()) {  
                String name = entry.getKey();  
                String value = entry.getValue();  
                if (!TextUtils.isEmpty(name) && value != null) {  
                    if (contentSb.length() > 0) {  
                        contentSb.append("&");  
                    }  
                    contentSb.append(Uri.encode(name, this.charset))  
                            .append("=")  
                            .append(Uri.encode(value, this.charset));  
                }  
            }  
        }  
  
        this.content = contentSb.toString().getBytes(this.charset);  
    }  
  
    @Override  
    public long getContentLength() {  
        return content.length;  
    }  
  
    @Override  
    public void setContentType(String contentType) {  
    }  
  
    @Override  
    public String getContentType() {  
        return "application/x-www-form-urlencoded;charset=" + charset;  
    }  
  
    @Override  
    public void writeTo(OutputStream sink) throws IOException {  
        sink.write(this.content);  
        sink.flush();  
    }  
}  