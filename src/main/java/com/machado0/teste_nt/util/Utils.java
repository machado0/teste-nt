package com.machado0.teste_nt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
