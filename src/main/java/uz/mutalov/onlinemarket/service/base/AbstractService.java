package uz.mutalov.onlinemarket.service.base;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import uz.mutalov.onlinemarket.mappers.base.BaseMapper;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public abstract class AbstractService<R extends BaseRepository, M extends BaseMapper> implements BaseService {

    protected final R repository;

    protected final M mapper;
}
