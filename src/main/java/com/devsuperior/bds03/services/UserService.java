package com.devsuperior.bds03.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds03.dto.RoleDTO;
import com.devsuperior.bds03.dto.UserDTO;
import com.devsuperior.bds03.dto.UserInsertDTO;
import com.devsuperior.bds03.dto.UserUpdateDTO;
import com.devsuperior.bds03.entities.Role;
import com.devsuperior.bds03.entities.User;
import com.devsuperior.bds03.repositories.RoleRepository;
import com.devsuperior.bds03.repositories.UserRepository;
import com.devsuperior.bds03.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable page) {
        Page<User> list = repository.findAll(page);
        return list.map(x -> new UserDTO(x));
    }

    @Transactional(readOnly = true)
    public UserDTO findbyId(Long id) {
        Optional<User> obj = repository.findById(id);
        User entity = obj.orElseThrow( () -> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
       try{
            User entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDTO(entity);
       }
       catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found" + id);
       }
    }    

    private void copyDtoToEntity(UserDTO dto, User entity){

        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for(RoleDTO roleDto : dto.getRoles()){
            Role role = roleRepository.getOne(roleDto.getId());
            entity.getRoles().add(role);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = repository.findByEmail(username);
        if(user == null){
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User found: " + username);
        return user;
    }
    
}
