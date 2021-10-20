package com.gergelytamas.brdwksttest.service;

import com.gergelytamas.brdwksttest.domain.BaseEntity;
import com.gergelytamas.brdwksttest.exception.MissingIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @param <S> domain
 * @param <U> repository
 */
@Transactional
@RequiredArgsConstructor
public class BaseServiceImpl<S extends BaseEntity, U extends JpaRepository<S, Integer>>
        implements BaseService<S> {

    protected final U repository;

    @Override
    public List<S> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<S> findById(final Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public S save(final S s) {
        return this.repository.save(s);
    }

    @Override
    public S update(final S s, final Integer id) {

        if (id == null) {
            throw new MissingIdException("Missing ID!");
        }

        return this.repository.save(s);
    }

    @Override
    public void delete(final Integer id) {
        this.repository.deleteById(id);
    }
}
