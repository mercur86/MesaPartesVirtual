package com.emtap.mesapartes.dto.reniec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    private String first_name;
    private String first_last_name;
    private String second_last_name;
    private String document_number;
}
