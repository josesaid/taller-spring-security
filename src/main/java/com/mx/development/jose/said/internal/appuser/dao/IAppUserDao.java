package com.mx.development.jose.said.internal.appuser.dao;

import com.mx.development.jose.said.internal.appuser.model.dto.AppUser;

import java.util.List;

public interface IAppUserDao {
    AppUser getByEmail(String email);
    AppUser getById(String id);
    List<AppUser> listAll();
    void create(AppUser appUser);
    void update(AppUser appUser);
}
