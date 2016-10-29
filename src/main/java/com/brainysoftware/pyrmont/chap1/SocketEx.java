package com.brainysoftware.pyrmont.chap1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * todo: 잘 안됨
 */
public class SocketEx {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 8080);

        OutputStream os = socket.getOutputStream();
        boolean autoflush = true;
        PrintWriter out = new PrintWriter(
                os, autoflush
        );
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        //웹 서버에 HTTP 요청을 전송
        out.println("GET /index.html HTTP/1.1");
        out.println("Host: localhost:8080");
        out.println("Connection: Close");
        out.println();

        //응답을 읽음
        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while (loop) {
            if (in.ready()) {
                int i = 0;
                while (i != -1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
            Thread.currentThread().sleep(50);
        }

        // 응답의 내용을 콘솔에 출력
        System.out.println(sb.toString());
        socket.close();

    }
}
