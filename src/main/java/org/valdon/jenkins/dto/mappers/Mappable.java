package org.valdon.jenkins.dto.mappers;


public interface Mappable<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

}
