/*
 * Copyright (c) Akveo 2019. All Rights Reserved.
 * Licensed under the Personal / Commercial License.
 * See LICENSE_PERSONAL / LICENSE_COMMERCIAL in the project root for license information on type of purchased license.
 */

package com.akveo.bundlejava.config;

import com.akveo.bundlejava.role.Role;
import com.akveo.bundlejava.user.User;
import com.akveo.bundlejava.user.UserDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        configModelMapper(modelMapper);
        return modelMapper;
    }

    private void configModelMapper(ModelMapper modelMapper) {
        configUserToUserDTOMapper(modelMapper);
        configUserDTOToUserMapper(modelMapper);
    }

    private void configUserToUserDTOMapper(ModelMapper modelMapper) {
        Converter<Set<Role>, Set<String>> converter =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());

        modelMapper.typeMap(User.class, UserDTO.class)
                .addMappings(mapper -> mapper.using(converter).map(User::getRoles, UserDTO::setRoles));
    }

    private void configUserDTOToUserMapper(ModelMapper modelMapper) {
        Converter<Set<String>, Set<Role>> converter =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().stream()
                        .map(roleName -> {
                            Role role = new Role();
                            role.setName(roleName);
                            return role;
                        })
                        .collect(Collectors.toSet());


        modelMapper.typeMap(UserDTO.class, User.class)
                .addMappings(mapper -> mapper.using(converter).map(UserDTO::getRoles, User::setRoles));
    }
}
