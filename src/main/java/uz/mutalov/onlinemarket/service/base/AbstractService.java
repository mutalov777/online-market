package uz.mutalov.onlinemarket.service.base;

import lombok.RequiredArgsConstructor;
import uz.mutalov.onlinemarket.mappers.base.BaseMapper;
import uz.mutalov.onlinemarket.repository.base.BaseRepository;

@RequiredArgsConstructor
public abstract class AbstractService<R extends BaseRepository, M extends BaseMapper> implements BaseService {

    protected final R repository;

    protected final M mapper;
}
