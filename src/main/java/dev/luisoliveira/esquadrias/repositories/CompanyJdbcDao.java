package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.dtos.resposeDto.AddressDTO;
import dev.luisoliveira.esquadrias.dtos.resposeDto.CompanyWithDetailsDTO;
import dev.luisoliveira.esquadrias.dtos.resposeDto.PhoneDTO;
import dev.luisoliveira.esquadrias.enums.PhoneType;
import dev.luisoliveira.esquadrias.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Repository
@Transactional
public class CompanyJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CompanyWithDetailsDTO getByIdWithAddressesAndPhones(UUID companyId) {
        String sql = "SELECT c.*, p.phone_id, p.phone_number, p.phone_type, a.* " +
                "FROM tb_companies c " +
                "LEFT JOIN tb_phones p ON c.company_id = p.company_company_id " +
                "LEFT JOIN tb_address a ON c.company_id = a.company_company_id " +
                "WHERE c.company_id = ?";

        return jdbcTemplate.query(sql, new Object[]{companyId}, rs -> {
            CompanyWithDetailsDTO companyDTO = null;
            Set<UUID> processedPhones = new HashSet<>();
            Set<UUID> processedAddresses = new HashSet<>();

            while (rs.next()) {
                if (companyDTO == null) {
                    companyDTO = new CompanyWithDetailsDTO();
                    companyDTO.setCompanyId(UUID.fromString(rs.getString("company_id")));
                    companyDTO.setCompanyName(rs.getString("company_name"));
                    companyDTO.setCnpj(rs.getString("cnpj"));
                    companyDTO.setStateRegistration(rs.getString("state_registration"));
                    companyDTO.setMunicipalRegistration(rs.getString("municipal_registration"));
                    companyDTO.setFantasyName(rs.getString("fantasy_name"));
                    companyDTO.setCategory(rs.getString("category"));
                    companyDTO.setEmail(rs.getString("email"));
                    companyDTO.setNameContact(rs.getString("name_contact"));
                    companyDTO.setSite(rs.getString("site"));
                    companyDTO.setDescription(rs.getString("description"));
                    companyDTO.setActive(rs.getBoolean("active"));
                    companyDTO.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    companyDTO.setResponsibleUser(new UserModel());

                    companyDTO.setPhones(new HashSet<>());
                    companyDTO.setAddresses(new HashSet<>());
                }

                UUID addressId = rs.getObject("address_id", UUID.class);
                if (addressId != null && !processedAddresses.contains(addressId)) {
                    AddressDTO address = new AddressDTO();
                    address.setAddressId(addressId);
                    address.setStreet(rs.getString("street"));
                    address.setCity(rs.getString("city"));
                    companyDTO.getAddresses().add(address);
                    processedAddresses.add(addressId);
                }
                UUID phoneId = rs.getObject("phone_id", UUID.class);
                if (phoneId != null && !processedPhones.contains(phoneId)) {
                    PhoneDTO phone = new PhoneDTO();
                    phone.setPhoneId(phoneId);
                    phone.setPhoneNumber(rs.getString("phone_number"));
                    phone.setPhoneType(PhoneType.valueOf(rs.getString("phone_type")));
                    companyDTO.getPhones().add(phone);
                    processedPhones.add(phoneId);
                }
            }
            return companyDTO;

        });


    }
}