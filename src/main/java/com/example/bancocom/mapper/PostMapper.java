package com.example.bancocom.mapper;

import com.example.bancocom.dtos.PostDTO;
import com.example.bancocom.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDTO convertirAPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setText(post.getText());

        if (post.getUsuario() != null) {
            postDTO.setIdUsuario(post.getUsuario().getId());
        }
        return postDTO;
    }
}

