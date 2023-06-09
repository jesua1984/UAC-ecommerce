package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.application.repository.UserRepository;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import com.UAC.ecommerce.infrastructure.entity.UserEntity;
import com.UAC.ecommerce.infrastructure.mapper.UserMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserCrudRepository userCrudRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserCrudRepository userCrudRepository, UserMapper userMapper) {
        this.userCrudRepository = userCrudRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(User user) {
        return userMapper.toUser(userCrudRepository.save(userMapper.toUserEntity(user)));
    }

 /*   @Override
    public User findByEmail(String email) {
        return userMapper.toUser(userCrudRepository.findByEmail(email).get());
    }*/

    @Override
    public User findByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userCrudRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            return userMapper.toUser(userEntity);
        }
        return null; // O lanza una excepción, dependiendo de tu lógica de negocio
    }




    @Override
    public User findById(Long id) {
        return userMapper.toUser(userCrudRepository.findById(id).get());
    }

    @Override
    public Iterable<User> getUsers() {
        return userMapper.toUser(userCrudRepository.findAll());
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.toUser(userCrudRepository.findById(id).get()) ;
    }

    @Override
    public Page<User> findByUserType(UserType userType, Pageable pageable) {
        return userMapper.toUserPage(userCrudRepository.findByUserType(UserType.USER, pageable));
    }

    @Override
    public User saveUser(User user) {
        return userMapper.toUser(userCrudRepository.save(userMapper.toUserEntity(user)));    }

    @Override
    public void deleteUserById(Long id) {
        userCrudRepository.deleteById(id);
    }


}
