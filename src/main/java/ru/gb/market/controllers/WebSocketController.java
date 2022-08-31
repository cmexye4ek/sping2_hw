package ru.gb.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.market.dto.WsMessage;
import ru.gb.market.services.FileService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ws")
public class WebSocketController {

    private final FileService fileService;

    @MessageMapping("/price")
    @SendTo("/topic/price")
    public WsMessage sendPrice() throws IOException, InterruptedException {
        Thread.sleep(10000); //имитация генерации прайса
        return new WsMessage(fileService.generatePrice());
    }
}
