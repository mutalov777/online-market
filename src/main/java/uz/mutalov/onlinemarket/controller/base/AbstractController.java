package uz.mutalov.onlinemarket.controller.base;

import uz.mutalov.onlinemarket.service.base.BaseService;

public abstract class AbstractController<S extends BaseService> implements BaseController {
    protected final S service;

    public AbstractController(S service) {
        this.service = service;
    }
}
