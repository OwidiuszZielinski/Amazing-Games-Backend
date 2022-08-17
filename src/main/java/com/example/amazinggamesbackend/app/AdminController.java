package com.example.amazinggamesbackend.app;

import com.example.amazinggamesbackend.core.extend.UserModel;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminController extends UserModel {

    @Override
    public Integer getId() {
        return super.getId();
    }
}
