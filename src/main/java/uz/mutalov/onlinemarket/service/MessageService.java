package uz.mutalov.onlinemarket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.criteria.MessageCriteria;
import uz.mutalov.onlinemarket.dto.message.MessageCreateDTO;
import uz.mutalov.onlinemarket.dto.message.MessageDTO;
import uz.mutalov.onlinemarket.dto.message.MessageUpdateDTO;
import uz.mutalov.onlinemarket.entity.AuthUser;
import uz.mutalov.onlinemarket.entity.Message;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.MessageMapper;
import uz.mutalov.onlinemarket.repository.MessageRepository;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.AbstractService;
import uz.mutalov.onlinemarket.service.base.GenericService;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageService extends AbstractService<MessageRepository, MessageMapper>
        implements GenericService<MessageDTO, MessageCriteria>,
        GenericCrudController<MessageDTO,MessageCreateDTO, MessageUpdateDTO> {
    private final AuthUserService authUserService;

    public MessageService(MessageRepository repository, @Qualifier("messageMapperImpl") MessageMapper mapper, AuthUserService authUserService) {
        super(repository, mapper);
        this.authUserService = authUserService;
    }

    @Override
    public ResponseEntity<DataDTO<MessageDTO>> create(MessageCreateDTO dto) {
        AuthUser to = authUserService.getAuthUserById(dto.getTo());
        Message message = mapper.fromCreateDTO(dto);
        message.setTo(to);
        message.setFrom(authUserService.getAuthUserByEmail());
        Message save = repository.save(message);
        MessageDTO messageDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(messageDTO));
    }

    @Override
    public ResponseEntity<DataDTO<MessageDTO>> update(MessageUpdateDTO dto) {
        Message message = getMessageById(dto.getId());
        Message message1 = mapper.fromUpdateDTO(dto, message);
        Message save = repository.save(message1);
        MessageDTO messageDTO = mapper.toDTO(save);
        return new ResponseEntity<>(new DataDTO<>(messageDTO));
    }

    private Message getMessageById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Message Not found"));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> delete(Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(new DataDTO<>(id));
    }

    @Override
    public ResponseEntity<DataDTO<MessageDTO>> get(Long id) {
        Message message = repository.getById(id);
        MessageDTO messageDTO = mapper.toDTO(message);
        return new ResponseEntity<>(new DataDTO<>(messageDTO));
    }

    @Override
    public ResponseEntity<DataDTO<List<MessageDTO>>> getAll(MessageCriteria criteria) {
        List<Message> all;
        if (Objects.nonNull(criteria.getText())) {
            all = repository.findAllByStartWith(criteria.getText()).orElse(new LinkedList<>());
        } else {
            all = repository.findAll();
        }
        List<MessageDTO> messageDTOS = mapper.toDTO(all);
        return new ResponseEntity<>(new DataDTO<>(messageDTOS, messageDTOS.size()));
    }
}
