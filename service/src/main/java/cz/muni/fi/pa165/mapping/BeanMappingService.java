package cz.muni.fi.pa165.mapping;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author František Hrdina
 */
public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);

    Mapper getMapper();
}
