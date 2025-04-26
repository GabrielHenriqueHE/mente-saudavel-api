package com.mentesaudavel.mentesaudavel.core.mappers;

import com.mentesaudavel.mentesaudavel.core.dto.in.UserCreateRequestDTO;
import com.mentesaudavel.mentesaudavel.core.dto.out.UserCreateResponseDTO;
import com.mentesaudavel.mentesaudavel.core.entities.User;

public class UserMapper {

    public static UserCreateResponseDTO entityToDTO (User user) {
        return new UserCreateResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getUserStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
