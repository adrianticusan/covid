package com.covid19.match.mappers;

import com.covid19.match.dtos.ContactDto;
import com.covid19.match.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact contactDtoToContact(ContactDto contactDto);

    ContactDto contactToContactDto(Contact contact);
}
