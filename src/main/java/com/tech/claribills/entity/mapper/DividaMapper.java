package com.tech.claribills.entity.mapper;

import com.tech.claribills.dtos.DividasConviteResponseDTO;
import com.tech.claribills.entity.ParticipanteDividas;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper
public interface DividaMapper {

    DividaMapper INSTANCE = Mappers.getMapper(DividaMapper.class);

    List<DividasConviteResponseDTO> convertParticipanteDividasToDto(Set<ParticipanteDividas> participante);

}
