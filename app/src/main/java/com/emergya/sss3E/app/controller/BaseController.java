package com.emergya.sss3E.app.controller;

import com.emergya.sss3E.app.businesslogic.BaseBusinessLogic;
import com.emergya.sss3E.app.common.SpringSoaStarterException;
import com.emergya.sss3E.app.controller.handler.ExceptionsHandler;
import com.emergya.sss3E.app.dto.BaseDTO;
import com.emergya.sss3E.app.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This abstract class is a management contract for the different Controller types.
 *
 * @param <E> entity type
 * @param <D> dto type
 * @author Rogelio Ramos rramos@emergya.com
 * @author Estefania Barrera ebarrera@emergya.com
 */
@RestController
public abstract class BaseController<E extends BaseEntity, D extends BaseDTO> extends ExceptionsHandler {

    /**
     * Generic method to gets all elements.
     *
     * @param since Page.
     * @param count Number of elements.
     * @param sort Keys separated by commas. Write '-' in the first character of the keyword to sort DESC.
     * @return Paginated Dto entity.
     */
    @GetMapping
    public Page<D> getAll(@RequestParam(required = false, name = "since", defaultValue = "0") Integer since,
            @RequestParam(required = false, name = "count", defaultValue = "10") Integer count,
            @RequestParam(required = false, name = "sort", defaultValue = "") String sort) {
        return this.getBusinessLogic().getAllEntities(since, count, sort);
    }

    /**
     * Generic method to get a element by id.
     *
     * @param id Identifier of element.
     * @return Dto entity.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<D> getEntity(@PathVariable Long id) {
        D entity = this.getBusinessLogic().getEntity(id);
        if (null != entity) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Generic method to create a entity.
     *
     * @param entityDTO Type of entityDTO (Staff, Guest,...).
     * @return dto entity.
     * @throws SpringSoaStarterException if save fails.
     * @throws URISyntaxException if URI is malformed.
     */
    @PostMapping
    public ResponseEntity<D> saveEntity(@RequestBody final D entityDTO)
            throws SpringSoaStarterException, URISyntaxException {

        return ResponseEntity.created(new URI(this.getUri())).body(this.getBusinessLogic().createEntity(entityDTO));
    }

    /**
     * Generic method to update a entity.
     *
     * @param id Identifier of element.
     * @param entityDTO Type of entityDTO (Broadcast, Message,...).
     * @return dto entity updated.
     * @throws SpringSoaStarterException if update fails.
     * @throws URISyntaxException if URI is malformed.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<D> updateEntity(@PathVariable final Long id, @RequestBody final D entityDTO)
            throws SpringSoaStarterException, URISyntaxException {

        return ResponseEntity.created(new URI(this.getUri())).body(this.getBusinessLogic().updateEntity(entityDTO, id));
    }

    /**
     * Generic method to delete a entity.
     *
     * @param id Identifier of element.
     * @return DTO entity updated.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<D> deleteEntity(@PathVariable Long id) {

        try {
            this.getBusinessLogic().deleteEntity(id);
            return ResponseEntity.noContent().build();
        } catch (SpringSoaStarterException e) {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Generic method to get business logic
     *
     * @return Object business logic.
     */
    protected abstract BaseBusinessLogic<E, D> getBusinessLogic();

    /**
     * Generic method to get a URL of endpoint.
     *
     * @return generic endpoint.
     */
    protected abstract String getUri();
}