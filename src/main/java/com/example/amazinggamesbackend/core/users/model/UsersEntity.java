package com.example.amazinggamesbackend.core.users.model;

import com.example.amazinggamesbackend.core.users.dto.UsersDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class UsersEntity {



        @Id
        @GeneratedValue
        private Integer id;

        private String username;
        private String password;
        private String email;
        private String accesslevel;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccesslevel() {
            return accesslevel;
        }

        public void setAccesslevel(String accesslevel) {
            this.accesslevel = accesslevel;
        }

        public void fromDTO(UsersDTO usersDTO){
        this.username = usersDTO.getUsername();
        this.email = usersDTO.getEmail();
        this.accesslevel = usersDTO.getAccesslevel();
        this.password = usersDTO.getPassword();

    }


}
