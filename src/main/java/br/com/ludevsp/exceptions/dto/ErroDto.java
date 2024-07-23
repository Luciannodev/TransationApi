package br.com.ludevsp.exceptions.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ErroDto {
    private  Map<String, String> errors;

    public ErroDto(Map<String, String> errors) {
        this.errors = errors;
    }
}
