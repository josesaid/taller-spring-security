package com.mx.development.jose.said.internal.appuser.service;

import com.mx.development.jose.said.internal.appuser.dao.IAppUserDao;
import com.mx.development.jose.said.internal.appuser.model.dto.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
@RequiredArgsConstructor
public class AppUserService {

    private final IAppUserDao userDao;

    public AppUser getById(String userId) {
        return this.userDao.getById(userId);
    }

    public List<AppUser> listAll() {
        return this.userDao.listAll();
    }

    public void update(AppUser user) {
        this.userDao.update(user);
    }

}
