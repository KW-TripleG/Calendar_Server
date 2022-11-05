package com.tgCalendar.tgCalendar.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;

@Data
public class Response {

    private StatusEnum status;
    private String message;
    private Object data;

    public Response() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }

    @Builder
    public Response(StatusEnum status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static HttpHeaders getDefaultHeader() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return headers;
    }
}
