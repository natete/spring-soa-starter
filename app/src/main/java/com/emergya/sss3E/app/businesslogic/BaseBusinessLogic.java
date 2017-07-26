package com.emergya.sss3E.app.businesslogic;

import com.emergya.sss3E.app.common.SpringSoaStarterException;
import com.emergya.sss3E.app.dto.BaseDTO;
import com.emergya.sss3E.app.model.BaseEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a common pattern to perform CRUD operations using Template Method Pattern.
 *
 * @author Rogelio Ramos rramos@emergya.com
 * @author Estefania Barrera ebarrera@emergya.com
 */
public abstract class BaseBusinessLogic<E extends BaseEntity, D extends BaseDTO> {

    /**
     * Retrieves a DTO entity with the given id.
     *
     * @param id the id of the entity to be retrieved.
     * @return the requested entity or null.
     */
    public D getEntity(final Long id) {
        return modelToDto(this.getRepository().findOne(id));
    }

    /**
     * Retrieves dto entities paginated.
     *
     * @param since Page
     * @param count Number of elements
     * @param sort Keys separated by commas. Write '-' in the first character of the keyword to sort DESC.
     * @return {@link Page} of entities.
     */
    public Page<D> getAllEntities(Integer since, Integer count, String sort) {
        PageRequest modelPageRequest = BaseBusinessLogic.getPageRequest(since, count, sort);

        Page<E> modelEntities = this.getRepository().findAll(modelPageRequest);

        List<D> result = new ArrayList<>();
        for (E E : modelEntities.getContent()) {
            result.add(modelToDto(E));
        }

        Pageable pageRequest = BaseBusinessLogic.getPageRequest(since, count, sort);
        return new PageImpl<>(result, pageRequest, modelEntities.getTotalElements());
    }

    /**
     * Persists the given entity if it passes validation and if it does not exist.
     *
     * @param entityDTO the model entityDTO to be persisted.
     * @return A dto object that represents the persisted entity.
     * @throws SpringSoaStarterException if validation fails.
     */
    public D createEntity(final D entityDTO) throws SpringSoaStarterException {
        if (entityDTO.getId() == null) {
            this.validateEntity(entityDTO);

            E persistedEntity = this.getRepository().save(this.dtoToModel(entityDTO));
            return modelToDto(persistedEntity);
        } else {
            throw new SpringSoaStarterException("It shouldn't have id");
        }
    }

    /**
     * Updates the given model entity if it passes validation and if it exists in the database.
     *
     * @param entityDTO the entityDTO to be updated.
     * @param id the id of the entity to be updated.
     * @return A dto object that represents the updated entity.
     * @throws SpringSoaStarterException if validation fails.
     */
    public D updateEntity(final D entityDTO, final Long id) throws SpringSoaStarterException {
        this.validateEntity(entityDTO);
        E persistedEntity = this.getRepository().findOne(id);
        if (persistedEntity == null) {
            throw new SpringSoaStarterException("The entity to update does not exist");
        } else {

            persistedEntity = this.getRepository().save(this.dtoToModel(entityDTO));
            return modelToDto(persistedEntity);
        }
    }

    /**
     * Deletes the entity with the given id from database.
     *
     * @param id the id of the entity to be deleted.
     * @return The deleted entity.
     * @throws SpringSoaStarterException if the entity does not exist.
     */
    public D deleteEntity(final Long id) throws SpringSoaStarterException {
        try {
            D entity = getEntity(id);
            this.getRepository().delete(id);
            return entity;
        } catch (EmptyResultDataAccessException e) {
            String message = "The entity to delete does not exist";
            throw new SpringSoaStarterException(message);
        }
    }

    /**
     * Validates the given entity.
     *
     * @param entityDTO the entityDTO to be validated.
     * @throws SpringSoaStarterException if validation fails.
     */
    protected abstract void validateEntity(D entityDTO) throws SpringSoaStarterException;

    /**
     * Parse Model entity to Dto entity
     *
     * @param entity Model class
     * @return Dto class
     */
    protected abstract D modelToDto(E entity);

    /**
     * Parse Model entity to Dto entity
     *
     * @param entityDTO DTO class
     * @return Dto class
     */
    protected abstract E dtoToModel(D entityDTO);

    /**
     * Get the repository to perform database actions.
     *
     * @return {@link PagingAndSortingRepository} repository.
     */
    protected abstract PagingAndSortingRepository<E, Long> getRepository();

    /**
     * Method to create a Page parameter.
     * Example: createPageRequest("0", "20", "type,-lastUpdate")
     * Result: Retrieves first page with 20 objects in ascending order of type. Within a specific type, newer objects
     * are ordered first.
     *
     * @param since Page
     * @param count Number of elements
     * @param sort Keys separated by commas. Write '-' in the first character of the keyword to sort DESC.
     * @return Page object.
     */
    private static PageRequest getPageRequest(Integer since, Integer count, String sort) {
        PageRequest pageRequest;

        //Check if request wants a sort page
        if (sort != null && !sort.trim().isEmpty()) {

            //Separates sort string
            String[] sortArray = sort.split(",");

            List<Sort.Order> sortOrderArray = new ArrayList<>();
            for (String sortKey : sortArray) {
                //If string has - in first char, set Desc order, else Asc
                Sort.Direction direction = (sortKey.charAt(0) == '-') ? Sort.Direction.DESC : Sort.Direction.ASC;
                sortOrderArray.add(new Sort.Order(direction, sortKey));
            }

            Sort sortObj = new Sort(sortOrderArray);

            pageRequest = new PageRequest(since, count, sortObj);
        } else {
            pageRequest = new PageRequest(since, count);
        }
        return pageRequest;
    }
}
