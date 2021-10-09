package com.redeempresarial.agent.aps.iso.client;

import com.redeempresarial.agent.aps.iso.web.YouTubeWeb;
import java.net.URLEncoder;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

@Path("/youtube")
@RequestScoped
public class Youtube {

    @GET
    @Path("/search/{video}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVideo(@PathParam("video") String video) {
        YouTubeWeb ytClient = new YouTubeWeb();
        JSONObject videos = new JSONObject();
        String retorno = "";
        try {
            retorno = ytClient.searchVideo(URLEncoder.encode(video, "UTF-8"));
            try {
                videos = new JSONObject(retorno);
            } catch (Exception e) {
                e.printStackTrace();
                return retorno;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos.toString();
    }
}
