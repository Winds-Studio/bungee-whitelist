package com.hadroncfy.bungeewhitelist;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.Charsets;

public class MinecraftAPI {
    private static final String HOST = "api.mojang.com";
    private static final String UUID_ENDPOINT = "/users/profiles/minecraft/";

    private static String doHttpGet(URL url) throws IOException {
        InputStream is = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setUseCaches(false);
            is = connection.getInputStream();
    
            return IOUtils.toString(is, Charsets.UTF_8);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static Profile getUUIDByName(String name) throws IOException {
        URL u = new URL("https", HOST, UUID_ENDPOINT + name);
        Gson gson = new Gson();
        String responseText = doHttpGet(u);
        if (responseText.equals("")){
            return null;
        }
        UUIDResponse response = gson.fromJson(responseText, UUIDResponse.class);
        UUID uuid = UUID.fromString(response.id.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        return new Profile(uuid, response.name);
    }

    public static Profile getOfflineUUID(String name){
        UUID uuid =  UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
        return new Profile(uuid, name);
    }
}