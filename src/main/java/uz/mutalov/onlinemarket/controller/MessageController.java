package uz.mutalov.onlinemarket.controller;

import org.springframework.web.bind.annotation.*;
import uz.mutalov.onlinemarket.controller.base.AbstractController;
import uz.mutalov.onlinemarket.controller.base.GenericController;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.criteria.MessageCriteria;
import uz.mutalov.onlinemarket.dto.message.MessageCreateDTO;
import uz.mutalov.onlinemarket.dto.message.MessageDTO;
import uz.mutalov.onlinemarket.dto.message.MessageUpdateDTO;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController extends AbstractController<MessageService> implements GenericController<MessageDTO, MessageCriteria>,
        GenericCrudController<MessageDTO,MessageCreateDTO, MessageUpdateDTO> {

    public MessageController(MessageService service) {
        super(service);
    }

    @Override
    @GetMapping("/get/{id}")
    public ResponseEntity<DataDTO<MessageDTO>> get(@PathVariable Long id) {
        return service.get(id);
    }

    @Override
    @GetMapping("/get-list")
    public ResponseEntity<DataDTO<List<MessageDTO>>> getAll(MessageCriteria criteria) {
        return service.getAll(criteria);
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
}
