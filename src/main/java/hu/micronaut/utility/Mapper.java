package hu.micronaut.utility;


import io.micronaut.data.exceptions.MappingException;
import lombok.experimental.UtilityClass;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class Mapper {
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());
    }

    public static <D, T> D map(final T entity, Class<D> outClass) {
        try {
            return modelMapper.map(entity, outClass);
        } catch (Exception e) {
            throw new MappingException(e.getMessage());
        }
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream().map(entity -> map(entity, outCLass)).toList();
    }

    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
