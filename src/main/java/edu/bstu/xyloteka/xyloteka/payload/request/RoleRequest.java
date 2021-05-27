package edu.bstu.xyloteka.xyloteka.payload.request;

import edu.bstu.xyloteka.xyloteka.models.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class RoleRequest {
    private String role;
}
