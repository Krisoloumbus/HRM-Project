/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.hrm.AppUpdate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Kris
 */
public class GithubReleaseClient {
    private GithubReleaseClient(){}

    // Change to your Git repo
    public static final String OWNER = "Krisoloumbus";
    public static final String REPO  = "HRM-Project";
    public static final String ASSET_NAME = "hrm-app.jar";
    public static Optional<String> GITHUB_TOKEN = Optional.empty(); // private repo -> Optional.of("ghp_xxx")

    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public static JSONObject fetchLatestRelease() throws Exception {
        String url = "https://api.github.com/repos/" + OWNER + "/" + REPO + "/releases/latest";
        HttpRequest.Builder rb = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/vnd.github+json");
        GITHUB_TOKEN.ifPresent(t -> rb.header("Authorization", "Bearer " + t));
        HttpResponse<String> res = http.send(rb.build(), HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() >= 300) throw new RuntimeException("GitHub API: " + res.statusCode() + " " + res.body());
        return new JSONObject(res.body());
    }

    public static String latestVersion(JSONObject rel) {
        String tag = rel.optString("tag_name","").replaceFirst("^v","");
        if (!tag.isBlank()) return tag;
        String name = rel.optString("name","").replaceFirst("^v","");
        return name.isBlank()? "0.0.0" : name;
    }

    public static Optional<String> assetUrl(JSONObject rel, String assetName) {
        JSONArray assets = rel.optJSONArray("assets");
        if (assets == null) return Optional.empty();
        for (int i=0;i<assets.length();i++) {
            var a = assets.getJSONObject(i);
            if (assetName.equalsIgnoreCase(a.optString("name",""))) {
                return Optional.ofNullable(a.optString("browser_download_url", null));
            }
        }
        return Optional.empty();
    }

    public static Path download(String url, Path to) throws Exception {
        HttpRequest.Builder rb = HttpRequest.newBuilder(URI.create(url))
                .GET().timeout(Duration.ofMinutes(3));
        GITHUB_TOKEN.ifPresent(t -> rb.header("Authorization", "Bearer " + t));
        HttpResponse<Path> res = http.send(rb.build(), HttpResponse.BodyHandlers.ofFile(to));
        if (res.statusCode() >= 300) throw new RuntimeException("Download failed: " + res.statusCode());
        return res.body();
    }
}
