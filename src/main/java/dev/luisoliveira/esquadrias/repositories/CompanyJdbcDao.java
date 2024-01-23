package dev.luisoliveira.esquadrias.repositories;

import dev.luisoliveira.esquadrias.dtos.resposeDto.AddressRespDTO;
import dev.luisoliveira.esquadrias.dtos.resposeDto.CompanyWithDetailsDTO;
import dev.luisoliveira.esquadrias.dtos.resposeDto.PhoneRespDTO;
import dev.luisoliveira.esquadrias.models.AddressModel;
import dev.luisoliveira.esquadrias.models.CompanyModel;
import dev.luisoliveira.esquadrias.models.PhoneModel;
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
            CompanyModel company = null;
            Set<UUID> processedPhones = new HashSet<>();
            Set<UUID> processedAddresses = new HashSet<>();

            while (rs.next()) {
                if (company == null) {
                    company = new CompanyModel();
                    company.setCompanyId(UUID.fromString(rs.getString("company_id")));
                    company.setCompanyName(rs.getString("company_name"));
                    company.setCnpj(rs.getString("cnpj"));
                    company.setStateRegistration(rs.getString("state_registration"));
                    company.setMunicipalRegistration(rs.getString("municipal_registration"));
                    company.setFantasyName(rs.getString("fantasy_name"));
                    company.setCategory(rs.getString("category"));
                    company.setEmail(rs.getString("email"));
                    company.setNameContact(rs.getString("name_contact"));
                    company.setSite(rs.getString("site"));
                    company.setDescription(rs.getString("description"));
                    company.setActive(rs.getBoolean("active"));
                    company.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
                    company.setResponsibleUser(new UserModel());

                    company.setPhones(new HashSet<>());
                    company.setAddress(new HashSet<>());
                }

                UUID addressId = rs.getObject("address_id", UUID.class);
                if (addressId != null && !processedAddresses.contains(addressId)) {
                    AddressRespDTO address = new AddressRespDTO();
                    address.setAddressId(addressId);
                    address.setStreet(rs.getString("street"));
                    address.setCity(rs.getString("city"));
                    AddressModel addressModel = new AddressModel();
                    addressModel.convertToAddressDTO();
                    company.getAddress().add(addressModel);
                    processedAddresses.add(addressId);
                }
                UUID phoneId = rs.getObject("phone_id", UUID.class);
                if (phoneId != null && !processedPhones.contains(phoneId)) {
                    PhoneRespDTO phone = new PhoneRespDTO();
                    phone.setPhoneId(phoneId);
                    phone.setPhoneNumber(rs.getString("phone_number"));
                    phone.setPhoneType(rs.getString("type"));// this line is the problem
                    PhoneModel phoneModel = new PhoneModel();
                    phoneModel.convertToPhoneDTO();
                    company.getPhones().add(phoneModel);
                    processedPhones.add(phoneId);
                }
            }
            return company.convertToCompanyEventDto();

        });
    }
}