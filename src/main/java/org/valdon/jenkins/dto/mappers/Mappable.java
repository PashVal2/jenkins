package org.valdom.jenkins.dto.mappers;


public interface Mappable<E, D> {

    E ToEntity(D dto);

    D toDto(E entity);

}
