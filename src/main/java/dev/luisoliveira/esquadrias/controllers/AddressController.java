package dev.luisoliveira.esquadrias.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dev.luisoliveira.esquadrias.dtos.AddressDto;
import dev.luisoliveira.esquadrias.enums.AddressType;
import dev.luisoliveira.esquadrias.models.AddressModel;
import dev.luisoliveira.esquadrias.services.AddressService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;


    //    @PostMapping("/{userId}/createAddress")
//    public ResponseEntity<Object> registerAddress(@RequestBody @Validated(AddressDto.AddressView.RegistrationPost.class)
//                                                  @JsonView(AddressDto.AddressView.RegistrationPost.class) AddressDto addressDto) {
//
//        log.debug("POST registerAddress AddressDto received: ------> {}", addressDto.toString());
//
//
//            if (addressDto.getAddressId() != null) {
//                return ResponseEntity.badRequest().body("The addressId field must be null");
//            }
//            var addressModel = new AddressModel();
//            BeanUtils.copyProperties(addressDto, addressModel);
//            addressModel.setType(AddressType.RESIDENTIAL);
//            addressService.save(addressModel);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(addressModel);
//
//    }
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/{userId}/createAddress")
    public ResponseEntity<Object> registerAddress(@RequestBody @Validated(AddressDto.AddressView.RegistrationPost.class)
                                                  @JsonView(AddressDto.AddressView.RegistrationPost.class) AddressDto addressDto) {

        log.debug("POST registerAddress AddressDto received: ------> {}", addressDto.toString());

        try {
            if (addressDto.getAddressId() != null) {
                return ResponseEntity.badRequest().body("The addressId field must be null");
            }
            var addressModel = new AddressModel();
            BeanUtils.copyProperties(addressDto, addressModel);
            addressModel.setType(AddressType.RESIDENTIAL);
            addressService.save(addressModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(addressModel);

        } catch (Exception e) {
            log.error("Erro específico ocorreu", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro específico: " + e.getMessage());


        }
    }
}