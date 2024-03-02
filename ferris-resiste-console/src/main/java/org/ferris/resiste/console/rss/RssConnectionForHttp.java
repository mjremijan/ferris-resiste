package org.ferris.resiste.console.rss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Michael
 */
class RssConnectionForHttp extends RssConnection {

    protected RssConnectionForHttp(RssUrl url) {
        super(url);
    }

    @Override
    protected InputStream getInputStream() throws IOException {    
        try {
            return getInputStream(Optional.of("deflate, br"));
        } catch (Exception ignore) {
            return getInputStream(Optional.empty());
        }
    }

    private InputStream getInputStream(Optional<String> acceptEncoding) throws IOException
    {            
        HttpURLConnection httpConnection = (HttpURLConnection)url.getUrl().openConnection();
        httpConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        httpConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        acceptEncoding.ifPresent(s -> httpConnection.setRequestProperty("accept-encoding", s));
        httpConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
        httpConnection.setRequestProperty("upgrade-insecure-requests", "1");
        httpConnection.connect();

        if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.out.printf("Response code is not OK...it is: %d%n", httpConnection.getResponseCode());
            String errorString = "UNABLE TO OBTAIN";
            try {
                errorString
                    = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream(), "UTF-8")).lines().collect(Collectors.joining("\n"));
            } catch (Exception ignore) {}

            throw new RuntimeException(
                String.format(
                    "HTTP response is not OK. CODE=%d, ERROR_STRING=\"%s\""
                    , httpConnection.getResponseCode()
                    , errorString
                )
            );
        }
        return httpConnection.getInputStream();
    }
}