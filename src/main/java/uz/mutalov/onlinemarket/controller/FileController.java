package uz.mutalov.onlinemarket.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.dto.admin.AdminPanelData;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController extends AbstractController<FileService> {

    public FileController(FileService service) {
        super(service);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public Object upload(@RequestParam("file") MultipartFile multipartFile) {
        return service.upload(multipartFile);
    }

    @GetMapping("/get-admin-panel-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataDTO<AdminPanelData>> getAdminPanelData(){
        return service.getAdminPanelData();
    }

}