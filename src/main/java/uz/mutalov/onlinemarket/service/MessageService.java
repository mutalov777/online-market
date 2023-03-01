package uz.mutalov.onlinemarket.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.mutalov.onlinemarket.controller.base.GenericCrudController;
import uz.mutalov.onlinemarket.dto.message.MessageCreateDTO;
import uz.mutalov.onlinemarket.dto.message.MessageDTO;
import uz.mutalov.onlinemarket.dto.message.MessageShortDTO;
import uz.mutalov.onlinemarket.dto.message.MessageUpdateDTO;
import uz.mutalov.onlinemarket.entity.AuthUser;
import uz.mutalov.onlinemarket.entity.Message;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.mappers.MessageMapper;
import uz.mutalov.onlinemarket.repository.MessageRepository;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.AbstractService;

import java.util.List;
import java.util.Objects;

@Service
public class MessageService extends AbstractService<MessageRepository, MessageMapper>
        implements GenericCrudController<MessageDTO, MessageCreateDTO, MessageUpdateDTO> {
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

    public ResponseEntity<DataDTO<List<MessageDTO>>> get(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<Message> messages = repository.findAllByFromId(id, email);
        messages.forEach(message -> {
            if (Objects.equals(message.getTo().getEmail(), email))
                message.setView(true);
        });
        List<Message> messages1 = repository.saveAll(messages);
        List<MessageDTO> messageDTOS = mapper.toDTO(messages1);
        return new ResponseEntity<>(new DataDTO<>(messageDTOS, (long) messageDTOS.size()));
    }

    public ResponseEntity<DataDTO<List<MessageShortDTO>>> getAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<Long> userIds = repository.findAllByMessageTableExist(email);
        List<MessageShortDTO> messageShortDTOS = userIds.stream().map(id -> {
            List<Message> messages = repository.findAllMessageByFromIdAndToEmail(id, email);
            return new MessageShortDTO(messages.size(), id, Objects.equals(messages.get(0).getFrom().getEmail(), email) ? messages.get(0).getTo().getFullName() : messages.get(0).getFrom().getFullName());
        }).toList();
        return new ResponseEntity<>(new DataDTO<>(messageShortDTOS));
    }

    public ResponseEntity<DataDTO<List<MessageDTO>>> getAllByText(String text) {
        List<Message> messages = repository.findAllByStartWith(text)
                .orElseThrow(() -> new NotFoundException("Message not found"));
        List<MessageDTO> messageDTOS = mapper.toDTO(messages);
        return new ResponseEntity<>(new DataDTO<>(messageDTOS, (long) messageDTOS.size()));
    }
}
