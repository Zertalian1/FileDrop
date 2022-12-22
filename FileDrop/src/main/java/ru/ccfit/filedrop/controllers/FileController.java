package ru.ccfit.filedrop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.ccfit.filedrop.dto.FileDto;
import ru.ccfit.filedrop.dto.OrderDto;
import ru.ccfit.filedrop.dto.UserDto;
import ru.ccfit.filedrop.service.implement.FileServiceImpl;

@Controller
@AllArgsConstructor
public class FileController {
    FileServiceImpl fileService;

    @GetMapping("/blog/{orderId}/{fileId}/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, @PathVariable String orderId) {
        ByteArrayResource resource = fileService.downloadFile(Long.parseLong(fileId));
        return ResponseEntity.ok().headers(this.headers(fileService.getFileById(Long.parseLong(fileId))))
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType
                        ("application/octet-stream")).body(resource);
    }

    private HttpHeaders headers(FileDto name) {

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + name.getName());
        header.add("Cache-Control", "no-cache, no-store,"
                + " must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return header;

    }

    static void SafeOrderFile(MultipartFile file, UserDto user, OrderDto orderDto, FileServiceImpl fileService){
        FileDto fileDto = new FileDto(file.getOriginalFilename(), user, orderDto);
        fileService.saveFile(fileDto, file);
    }
}
