package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO createPerson(PersonDTO personDTO) {

        Person personToSave = personMapper.toModel(personDTO);

        Person personSaved = personRepository.save(personToSave);
        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + personSaved.getId())
                .build();
    }

    public List<PersonDTO> getAllPersons() {

        List<Person> personList = personRepository.findAll();
//        List<PersonDTO> personDTOList = new ArrayList<>();
//        personList.forEach(person -> personDTOList.add(personMapper.toDTO(person)));
//
//        return personDTOList;
        return personList.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }
}
