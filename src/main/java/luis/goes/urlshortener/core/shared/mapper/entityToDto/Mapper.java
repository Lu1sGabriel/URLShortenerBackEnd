package luis.goes.urlshortener.core.shared.mapper.entityToDto;

import java.util.List;

public interface Mapper<ResponseDTO extends DTO, Entity extends Mappable> {

    ResponseDTO toDto(Entity entity);

    List<ResponseDTO> toDtoList(List<Entity> entityList);

}