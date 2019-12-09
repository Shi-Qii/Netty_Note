package com.frizo.fatcat.chat.server.handler;

import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpRequestHandlerTest {
    @Test
    public void TestURLAndIndex() throws URISyntaxException, FileNotFoundException {
        URL location = HttpRequestHandler.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();
        String path = location.toURI() + "index.html";
        path = path.substring(6);
        File index = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(index));
        reader.lines().forEach(System.out::println);
    }


}
