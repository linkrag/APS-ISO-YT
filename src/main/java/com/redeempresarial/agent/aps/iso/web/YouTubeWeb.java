package com.redeempresarial.agent.aps.iso.web;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Felipe
 */
public class YouTubeWeb {

    private String apiToken = "AIzaSyDXwNyVVEPzwUqkL68i3Eo9Ysnyi5CiIaw";
    private String url = "https://www.googleapis.com/youtube/v3/search";

    public String searchVideo(String search) {
        String videos = "";

        try {
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,
                    new TrustManager[]{new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {

                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }}, new SecureRandom());
            SSLSocketFactory ssf = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            DefaultHttpClient httpclient = new DefaultHttpClient();

            ClientConnectionManager ccm1 = httpclient.getConnectionManager();
            SchemeRegistry sr1 = ccm1.getSchemeRegistry();
            sr1.register(new Scheme("https", 443, ssf));

            HttpResponse response = httpclient.execute(new HttpGet(url + "?key=" + apiToken + "&part=snippet&q=" + search + "&maxResults=10&order=relevance&videoDuration=any&type=video"));
            if (response.getStatusLine().getStatusCode() == 200) {
                videos = EntityUtils.toString(response.getEntity());
            } else {
                videos = response.getStatusLine().toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return videos.toString();
    }
}
