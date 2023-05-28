package com.UAC.ecommerce.infrastructure.adapter;

import com.UAC.ecommerce.application.repository.UserRepository;
import com.UAC.ecommerce.domain.User;
import com.UAC.ecommerce.domain.UserType;
import com.UAC.ecommerce.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

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

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(userCrudRepository.findByEmail(email).get());
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
    public Iterable<User> findByUserType(UserType userType) {
        return userMapper.toUser(userCrudRepository.findByUserType(UserType.USER));
    }

    @Override
    public User saveUser(User user) {
        return userMapper.toUser(userCrudRepository.save(userMapper.toUserEntity(user)));    }

    @Override
    public void deleteUserById(Long id) {
        userCrudRepository.deleteById(id);
    }

}
