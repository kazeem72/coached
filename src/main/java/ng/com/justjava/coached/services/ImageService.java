package ng.com.justjava.coached.services;
import ng.com.justjava.coached.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageService {

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ImageRepository imageRepository;


    public ResponseEntity<Map> uploadImage(Image image) {
        try {
            if (image.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (image.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            image.setUrl(cloudinaryService.uploadFile(image.getFile(), "folder_1"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}