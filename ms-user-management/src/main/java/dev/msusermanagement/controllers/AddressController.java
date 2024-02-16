package dev.msusermanagement.controllers;

import com.fasterxml.jackson.annotation.JsonView;

import dev.msusermanagement.configurations.security.UserDetailsImpl;
import dev.msusermanagement.dtos.AddressDto;
import dev.msusermanagement.enums.AddressType;
import dev.msusermanagement.models.AddressModel;
import dev.msusermanagement.models.UserModel;
import dev.msusermanagement.services.AddressService;
import dev.msusermanagement.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;



    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/register-user-address")
    public ResponseEntity<Object> registerUserAddress(@RequestBody
                                                      @Validated(AddressDto.AddressView.RegistrationPost.class)
                                                      @JsonView(AddressDto.AddressView.RegistrationPost.class)
                                                      AddressDto addressDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserModel loggedInUser = userService.findById(userDetails.getUserId()).get();
        log.debug("POST registerAddress AddressDto received: ------> {}", addressDto.toString());
        Optional<UserModel> userModelOptional = userService.findById(loggedInUser.getUserId());

        try {
            if (!userModelOptional.isPresent()) {
                log.warn("Username {} is Already Taken ", userDetails.getUserId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            var addressModel = new AddressModel();
            BeanUtils.copyProperties(addressDto, addressModel);
            if (addressDto.getType() == null){
            addressModel.setType(AddressType.RESIDENTIAL);
            }
            addressModel.setUserAddress(userModelOptional.get());
            addressService.save(addressModel);
            log.debug("POST registerAddress AddressModel created: ------> {}", addressModel.getAddressId());
            log.info("Address created successfully ------> addressId: {} ", addressModel.getAddressId());
            return ResponseEntity.status(HttpStatus.CREATED).body(addressModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/update-address/{addressId}")
    public ResponseEntity<Object> updateAddress(@PathVariable UUID addressId,
                                                @RequestBody
                                                @Validated(AddressDto.AddressView.RegistrationPost.class)
                                                @JsonView(AddressDto.AddressView.RegistrationPost.class)
                                                AddressDto addressDto, Authentication authentication) {

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            UUID userId = userDetails.getUserId();
            log.debug("Authentication {} --------> ", userDetails.getUsername());
            Optional<AddressModel> addressModelOptional = addressService.findById(addressId);
            if (addressModelOptional == null) {
                log.error("Address not found with addressId {}", addressId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found");
            }
            var addressModel = new AddressModel();
            BeanUtils.copyProperties(addressDto, addressModel);
            addressService.save(addressModel);
            log.info("Address updated successfully -------> addressId: {} ", addressModel.getAddressId());
            return ResponseEntity.ok(addressModel);
        } catch (Exception e) {
            log.error("Specific error occurred", e);
            throw e;
        }
    }
}