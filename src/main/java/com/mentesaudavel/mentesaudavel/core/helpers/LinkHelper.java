package com.mentesaudavel.mentesaudavel.core.helpers;

import com.mentesaudavel.mentesaudavel.core.dto.out.LinkResponseDTO;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class LinkHelper {

    public static LinkResponseDTO link(String path, HttpMethod method) {
        URI link = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path)
                .build()
                .toUri();

        return new LinkResponseDTO(method.name(), link);
    }
}
