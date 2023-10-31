package com.urbanlife.urbanlife.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
public class S3Controller {
    @Autowired
    S3Service s3Service;
    @Autowired
    S3Buckets s3Buckets;
    /*
    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String Key = s3Service.uploadFile(file);
        Map<String, String> resultado = new HashMap<String,String>();
        resultado.put("key", Key);
        resultado.put("URL", s3Service.getObjectUrl(Key));
        return resultado;
    }*/
    @GetMapping(
            value = "/get-object/{key}/categoria-images",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    ResponseEntity<ByteArrayResource> getObject(@PathVariable String key) {
        ByteArrayResource resource = new ByteArrayResource(s3Service.getObjectBytes(s3Buckets.getCustomer(), key));
        return ResponseEntity
                .ok()
                .body(resource);
    }
    @GetMapping("/{key}")
    public void getURLS(@PathVariable String key) {
        s3Service.getURL(s3Buckets.getCustomer(),key);
    }
}
