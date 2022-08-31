package ru.gb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.gb.market.services.FileService;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping(path = "/price/{filename}", produces = "application/octet-stream")
    public ResponseEntity<byte[]> getPrice(@PathVariable("filename") String fileName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(fileName).build().toString());
        try {
            return ResponseEntity.ok().headers(httpHeaders).body(fileService.getPriceFile(fileName));
        } catch (IOException e) {
            return new ResponseEntity<>(new byte[]{}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
