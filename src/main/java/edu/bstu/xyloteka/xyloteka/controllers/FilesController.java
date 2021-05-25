package edu.bstu.xyloteka.xyloteka.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;
import edu.bstu.xyloteka.xyloteka.models.FileInfo;
import edu.bstu.xyloteka.xyloteka.models.Photo;
import edu.bstu.xyloteka.xyloteka.models.Sample;
import edu.bstu.xyloteka.xyloteka.payload.response.MessageResponse;
import edu.bstu.xyloteka.xyloteka.repo.PhotoRepository;
import edu.bstu.xyloteka.xyloteka.repo.SampleRepository;
import edu.bstu.xyloteka.xyloteka.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/images")
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @Autowired
    PhotoRepository repo;

    @Autowired
    SampleRepository sampleRepo;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

    @PostMapping("/upload/{sample}")
    public ResponseEntity<MessageResponse> uploadFile(@PathVariable("sample") long id, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);
            Photo photo = new Photo();
            photo.setPhoto(file.getOriginalFilename());
            Sample sample = sampleRepo.findById(id).get();
            photo.setSample(sample);
            repo.save(photo);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }


    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
//            String url = MvcUriComponentsBuilder
//                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
            String url = "http://localhost:8080/api/images/" + path.getFileName().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("{sample}/{name}")
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto(@PathVariable("sample") long id, @PathVariable("name") String name) {
        try {

            Photo photo = repo.findBySample_idAndPhoto(id, name);

            if (photo == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            byte[] image = storageService.getPhoto(photo.getPhoto());

            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{sample}")
    @ResponseBody
    public ResponseEntity<List<byte[]>> getPhotosBySampleId(@PathVariable("sample") long id) {
        try {
            List<byte[]> images = new ArrayList<>();

            List<Photo> photos = repo.findBySample_id(id);

            for (Photo p : photos ) {
                images.add(storageService.getPhoto(p.getPhoto()));
            }

            if (images.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
