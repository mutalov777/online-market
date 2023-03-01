package uz.mutalov.onlinemarket.controller;

import org.springframework.web.bind.annotation.*;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.dto.message.MessageCreateDTO;
import uz.mutalov.onlinemarket.dto.message.MessageDTO;
import uz.mutalov.onlinemarket.dto.message.MessageShortDTO;
import uz.mutalov.onlinemarket.dto.message.MessageUpdateDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController extends AbstractController<MessageService>
        implements GenericCrudController<MessageDTO, MessageCreateDTO, MessageUpdateDTO> {

    public MessageController(MessageService service) {
        super(service);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DataDTO<List<MessageDTO>>> get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/get-list")
    public ResponseEntity<DataDTO<List<MessageShortDTO>>> getAll() {
        return service.getAll();
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<DataDTO<MessageDTO>> create(@RequestBody MessageCreateDTO dto) {
        return service.create(dto);
    }

    @Override
    @PostMapping("/update")
    public ResponseEntity<DataDTO<MessageDTO>> update(@RequestBody MessageUpdateDTO dto) {
        return service.update(dto);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataDTO<Long>> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/get-by-text/{text}")
    public ResponseEntity<DataDTO<List<MessageDTO>>> getAllByText(@PathVariable String text) {
        return service.getAllByText(text);
    }
}
