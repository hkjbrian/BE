package SJUCapstone.BE.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
    @Autowired
    S3ImageService s3ImageService;

    @PostMapping("/s3/upload")
    public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image) {
        String profileImage = s3ImageService.upload(image);
        return ResponseEntity.ok(profileImage);
    }

    @GetMapping("/s3/delete")
    public ResponseEntity<?> s3delete(@RequestParam("addr") String addr) {
        s3ImageService.deleteImageFromS3(addr);
        return ResponseEntity.ok(null);
    }
}
