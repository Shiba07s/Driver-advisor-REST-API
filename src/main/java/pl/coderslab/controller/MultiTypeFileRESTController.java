package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.errorhandler.exception.EntityNotFoundException;
import pl.coderslab.impl.MultiTypeFileServiceImpl;
import pl.coderslab.model.MultiTypeFile;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/file")
public class MultiTypeFileRESTController {

    private final MultiTypeFileServiceImpl multiTypeFileService;

    @Autowired
    public MultiTypeFileRESTController(MultiTypeFileServiceImpl multiTypeFileService) {
        this.multiTypeFileService = multiTypeFileService;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        multiTypeFileService.saveFile(file);;
    }

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<?> getImgById(@PathVariable("id") Long fileId) throws EntityNotFoundException {
        return multiTypeFileService.upload(fileId);
    }

    @PostMapping(path = "/update/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public MultiTypeFile updateImg(@RequestPart("file") MultipartFile file,
                                       @PathVariable("id") Long fileId) {
        MultiTypeFile foundedImg = multiTypeFileService.findById(fileId);
        multiTypeFileService.updateFile(file, fileId);
        return foundedImg;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Long imageId) throws EntityNotFoundException {
        multiTypeFileService.findById(imageId);
    }

}


//    @GetMapping("/find/{id}")
//    public ResponseEntity<Image> getImg(@PathVariable("id") Long id) {
//        Image image = imageService.findById(id);
//        return ResponseEntity.ok().body(image);
////                .contentType(MediaType.parseMediaType(image.getFileType()))
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + image.getFileName() + "\"")
////                .body(new ByteArrayResource(image.getData()));
//    }