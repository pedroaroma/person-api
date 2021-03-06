package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return createMessageResponse(personSaved.getId(), "Created person with ID ");
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

    public PersonDTO findById(Long id) throws PersonNotFoundException {

//        Optional<Person> personOptional = Optional.ofNullable(personRepository.findById(id)
//                .orElseThrow(() -> new PersonNotFoundException(id)));
//
//        PersonDTO personDTO = personMapper.toDTO(personOptional.get());

        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);

    }

    public void deleteById(Long id) throws PersonNotFoundException {

        verifyIfExists(id);

        personRepository.deleteById(id);

    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException {

        verifyIfExists(id);

        Person personToSave = personMapper.toModel(personDTO);

        Person personToUpdate = personRepository.save(personToSave);

        return createMessageResponse(personToUpdate.getId(), "Updated person with ID ");

    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO
                .builder()
                .message(s + id)
                .build();
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

}
