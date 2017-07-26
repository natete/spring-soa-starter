package com.emergya.sss3E.app.businesslogic;

import com.emergya.sss3E.app.common.SpringSoaStarterException;
import com.emergya.sss3E.app.dto.BaseDTO;
import com.emergya.sss3E.app.dto.UserDTO;
import com.emergya.sss3E.app.model.BaseEntity;
import com.emergya.sss3E.app.model.User;
import com.emergya.sss3E.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessLogic<E extends BaseEntity, D extends BaseDTO> extends BaseBusinessLogic<User, UserDTO> {

    private final UserRepository userRepository;

    @Autowired
    public UserBusinessLogic(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void validateEntity(UserDTO entityDTO) throws SpringSoaStarterException {

    }

    @Override
    protected UserDTO modelToDto(User user) {
        return new UserDTO(user);
    }

    @Override
    protected User dtoToModel(UserDTO userDTO) {
        return new User(userDTO);
    }

    @Override
    protected PagingAndSortingRepository<User, Long> getRepository() {
        return this.userRepository;
    }
}
