package com.batch.demo.processor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthdate;
}
