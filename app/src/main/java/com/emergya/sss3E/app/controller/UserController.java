package com.emergya.sss3E.app.controller;

import com.emergya.sss3E.app.businesslogic.BaseBusinessLogic;
import com.emergya.sss3E.app.businesslogic.UserBusinessLogic;
import com.emergya.sss3E.app.dto.UserDTO;
import com.emergya.sss3E.app.model.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that contains test methods.
 *
 * @author iiglesias
 */
@RestController
@RequestMapping(value = "/api/user")
@Api(value = "Services that contains test methods")
class UserController extends BaseController<User, UserDTO> {

    private final UserBusinessLogic userBusinessLogic;

    @Autowired
    public UserController(UserBusinessLogic userBusinessLogic) {
        this.userBusinessLogic = userBusinessLogic;
    }

    @Override
    protected BaseBusinessLogic<User, UserDTO> getBusinessLogic() {
        return userBusinessLogic;
    }

    @Override
    protected String getUri() {
        return "/api/user";
    }
}
