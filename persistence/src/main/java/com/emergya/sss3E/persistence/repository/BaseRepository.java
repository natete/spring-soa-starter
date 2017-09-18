package com.emergya.sss3E.persistence.repository;

import com.emergya.sss3E.persistence.model.BaseEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<E extends BaseEntity> extends PagingAndSortingRepository<E, Long>,
        QueryDslPredicateExecutor<E> {

}
