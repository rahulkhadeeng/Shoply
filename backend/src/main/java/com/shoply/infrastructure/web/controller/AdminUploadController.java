package com.shoply.infrastructure.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/uploads")
public class AdminUploadController {
    private static final int MAX_IMAGES = 3;
    private static final long MAX_IMAGE_SIZE = 4L * 1024 * 1024;
    private final String uploadThingToken;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public AdminUploadController(@Value("${UPLOADTHING_TOKEN:}") String uploadThingToken, ObjectMapper objectMapper) {
        this.uploadThingToken = uploadThingToken;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/product-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProductImages(@RequestPart("files") List<MultipartFile> files) {
        if (uploadThingToken.isBlank() || uploadThingToken.contains("dummy")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new UploadError("UploadThing is not configured on the backend."));
        }
        if (files.isEmpty() || files.size() > MAX_IMAGES) {
            return ResponseEntity.badRequest().body(new UploadError("Upload between 1 and 3 images."));
        }
        try {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                validate(file);
                urls.add(upload(file));
            }
            return ResponseEntity.ok(new UploadedImages(urls));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new UploadError(exception.getMessage()));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new UploadError(exception.getMessage() == null ? "UploadThing could not upload the images." : exception.getMessage()));
        }
    }

    private void validate(MultipartFile file) {
        if (file.isEmpty() || file.getSize() > MAX_IMAGE_SIZE) throw new IllegalArgumentException("Each image must be 4 MB or smaller.");
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) throw new IllegalArgumentException("Only image files are allowed.");
    }

    private String upload(MultipartFile file) throws Exception {
        String requestJson = objectMapper.writeValueAsString(new PrepareUpload(file.getContentType(), safeName(file), file.getSize(), "public-read"));
        HttpRequest prepareRequest = HttpRequest.newBuilder(URI.create("https://api.uploadthing.com/v7/prepareUpload"))
                .header("Content-Type", "application/json")
                .header("X-Uploadthing-Api-Key", apiKey())
                .header("X-Uploadthing-Fe-Package", "shoply-spring")
                .header("X-Uploadthing-Version", "7.7.4")
                .POST(HttpRequest.BodyPublishers.ofString(requestJson)).build();
        HttpResponse<String> prepared = httpClient.send(prepareRequest, HttpResponse.BodyHandlers.ofString());
        if (prepared.statusCode() / 100 != 2) throw new IllegalStateException("UploadThing prepare failed");
        JsonNode preparedJson = objectMapper.readTree(prepared.body());
        String signedUrl = preparedJson.path("url").asText();
        if (signedUrl.isBlank()) throw new IllegalStateException("UploadThing did not return an upload URL");
        String boundary = "Shoply" + UUID.randomUUID();
        HttpRequest uploadRequest = HttpRequest.newBuilder(URI.create(signedUrl))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .PUT(HttpRequest.BodyPublishers.ofByteArray(multipart(boundary, file))).build();
        HttpResponse<String> uploaded = httpClient.send(uploadRequest, HttpResponse.BodyHandlers.ofString());
        if (uploaded.statusCode() / 100 != 2) throw new IllegalStateException("UploadThing file transfer failed");
        JsonNode uploadedJson = objectMapper.readTree(uploaded.body());
        String directUrl = uploadedJson.path("url").asText(uploadedJson.path("data").path("url").asText());
        if (!directUrl.isBlank()) return directUrl;
        String key = preparedJson.path("key").asText(preparedJson.path("fileKey").asText());
        if (key.isBlank()) throw new IllegalStateException("UploadThing did not return a file key");
        return "https://utfs.io/f/" + key;
    }

    private static byte[] multipart(String boundary, MultipartFile file) throws Exception {
        ByteArrayOutputStream body = new ByteArrayOutputStream();
        String headers = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"file\"; filename=\"" + safeName(file) + "\"\r\nContent-Type: " + file.getContentType() + "\r\n\r\n";
        body.write(headers.getBytes(StandardCharsets.UTF_8)); body.write(file.getBytes()); body.write(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
        return body.toByteArray();
    }

    /** UploadThing v7 tokens contain the API key required by its prepareUpload REST endpoint. */
    private String apiKey() throws Exception {
        try {
            String encoded = uploadThingToken.startsWith("ey") ? uploadThingToken : uploadThingToken.substring(uploadThingToken.indexOf('.') + 1);
            JsonNode token = objectMapper.readTree(new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8));
            String apiKey = token.path("apiKey").asText();
            if (!apiKey.isBlank()) return apiKey;
        } catch (Exception ignored) { }
        throw new IllegalStateException("The configured UPLOADTHING_TOKEN is not a valid V7 token. Copy it from UploadThing Dashboard → API Keys → V7.");
    }

    private static String safeName(MultipartFile file) { return (file.getOriginalFilename() == null ? "product-image" : file.getOriginalFilename()).replaceAll("[^a-zA-Z0-9._-]", "-"); }
    private record PrepareUpload(String fileType, String fileName, long fileSize, String acl) { }
    private record UploadedImages(List<String> urls) { }
    private record UploadError(String message) { }
}
