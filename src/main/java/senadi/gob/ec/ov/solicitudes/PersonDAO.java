/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package senadi.gob.ec.ov.solicitudes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import senadi.gob.ec.ov.model.Person;
import senadi.gob.ec.ov.util.Operations;

/**
 *
 * @author michael
 */
public class PersonDAO {

    public Person getPersonFromSolicitudes(Person person) {
        String query = "Select p.*,c.* from person as p "
                + "inner join countries as c on c.id = p.nationality"
                + "where p.id = " + person.getId()+" ";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                person.setIdentificationType(rs.getString("p.identification_type"));
                person.setIdentificationNumber(rs.getString("p.identification_number"));
                person.setAddress(rs.getString("p.address"));
                person.setEmail(rs.getString("p.email"));
                person.setPhone(rs.getString("p.phone"));
                person.setNationality(rs.getString("c.name"));
            }
            con.close();
            return person;
        } catch (Exception ex) {
            System.out.println("error en obtener datos de person_id " + person.getId() + ": " + ex);
            return person;
        }
    }
    
    public List<Province> getProvincesByCountryId(Integer countryId) {
        String query = "Select * from provinces where country_id = " + countryId;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<Province> provinces = new ArrayList<>();
            while (rs.next()) {
                Province province = new Province();
                province.setId(rs.getInt("id"));
                province.setName(rs.getString("name"));
                province.setCountryId(rs.getInt("country_id"));
                provinces.add(province);
            }
            con.close();
            return provinces;
        } catch (Exception ex) {
            System.out.println("error en obtener datos provinces de country_id " + countryId + ": " + ex);
            return new ArrayList<>();
        }
    }

    public List<City> getCitiesByCountryId(Integer provinceId) {
        String query = "Select * from cities where province_id = " + provinceId;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<City> cities = new ArrayList<>();
            while (rs.next()) {
                City province = new City();
                province.setId(rs.getInt("id"));
                province.setName(rs.getString("name"));
                province.setProvinceId(rs.getInt("province_id"));
                cities.add(province);
            }
            con.close();
            return cities;
        } catch (Exception ex) {
            System.out.println("error en obtener datos cities de provice_id " + provinceId + ": " + ex);
            return new ArrayList<>();
        }
    }
    
    public List<Country> getCountries() {
        String query = "Select * from countries";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<Country> countries = new ArrayList<>();
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
                country.setCode(rs.getString("code"));
                countries.add(country);
            }
            con.close();
            return countries;
        } catch (Exception ex) {
            System.out.println("error en obtener datos countries: " + ex);
            return new ArrayList<>();
        }
    }
    
    public List<Person> getPersonsByIds(String ids){
        String query = "Select p.*,c.* from person as p "
                + "inner join countries as c on c.id = p.nationality "
                + "where p.id in ("+ids+")";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<Person> persons = new ArrayList<>();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("p.id"));
                person.setAddress(rs.getString("p.address"));
                person.setCityAddress(rs.getInt("p.city_address"));
                person.setDateBirth(rs.getDate("p.date_birth"));
                person.setEmail(rs.getString("p.email"));
                person.setGender(rs.getString("p.gender"));
                person.setIdentificationNumber(rs.getString("p.identification_number"));
                person.setIdentificationType(rs.getString("p.identification_type"));
                person.setName(rs.getString("p.name"));
                person.setCountryId(rs.getInt("p.nationality"));
                person.setPhone(rs.getString("p.phone"));
                person.setNationality(rs.getString("c.name"));
                persons.add(person);
            }
            con.close();
            return persons;
        } catch (Exception ex) {
            System.out.println("error en obtener datos person por ids " + ids + ": " + ex);
            return new ArrayList<>();
        }
    }

    public Person getPersonByIdentificationAndType(String identification, String type) {
        String query = "Select p.*,c.* from person as p "
                + "inner join countries as c on c.id = p.nationality "
                + "where p.identification_number = '" + identification + "' and p.identification_type = '" + type + "'";                
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Person person = new Person();
            while (rs.next()) {
                person.setId(rs.getInt("p.id"));
                person.setAddress(rs.getString("p.address"));
                person.setCityAddress(rs.getInt("p.city_address"));
                person.setDateBirth(rs.getDate("p.date_birth"));
                person.setEmail(rs.getString("p.email"));
                person.setGender(rs.getString("p.gender"));
                person.setIdentificationNumber(rs.getString("p.identification_number"));
                person.setIdentificationType(rs.getString("p.identification_type"));
                person.setName(rs.getString("p.name"));
                person.setCountryId(rs.getInt("p.nationality"));
                person.setPhone(rs.getString("p.phone"));
                person.setNationality(rs.getString("c.name"));
                break;
            }
            con.close();
            return person;
        } catch (Exception ex) {
            System.out.println("error en obtener datos person por " + identification + " y " + type + ": " + ex);
            return new Person();
        }
    }
    
    public boolean existsIdentification(String identificationNumber){
        String query = "Select p.*,c.* from person as p "
                + "inner join countries as c on c.id = p.nationality "
                + "where p.identification_number = '" + identificationNumber + "'";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Person person = new Person();
            while (rs.next()) {
                person.setId(rs.getInt("p.id"));
                person.setAddress(rs.getString("p.address"));
                person.setCityAddress(rs.getInt("p.city_address"));
                person.setDateBirth(rs.getDate("p.date_birth"));
                person.setEmail(rs.getString("p.email"));
                person.setGender(rs.getString("p.gender"));
                person.setIdentificationNumber(rs.getString("p.identification_number"));
                person.setIdentificationType(rs.getString("p.identification_type"));
                person.setName(rs.getString("p.name"));
                person.setCountryId(rs.getInt("p.nationality"));
                person.setPhone(rs.getString("p.phone"));
                person.setNationality(rs.getString("c.name"));
                break;
            }
            con.close();
            return person.getId() != null;
            
        } catch (Exception ex) {
            System.out.println("error en obtener datos person por " + identificationNumber + ": " + ex);
            return false;
        }
    }

    public Country getCountryById(Integer countryId) {
        String query = "Select * from countries where id = " + countryId;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Country country = new Country();
            while (rs.next()) {
                country.setId(rs.getInt("id"));
                country.setName(rs.getString("name"));
                country.setCode(rs.getString("code"));
            }
            con.close();
            return country;
        } catch (Exception ex) {
            System.out.println("error en obtener datos country " + countryId + ": " + ex);
            return new Country();
        }
    }

    public Integer getProvinceIdByCityId(Integer cityId) {
        String query = "Select * from cities where id = " + cityId;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            int provinceId = 0;
            while (rs.next()) {

                provinceId = rs.getInt("province_id");
            }
            con.close();
            return provinceId;
        } catch (Exception ex) {
            System.out.println("error en obtener datos provice id by " + cityId + ": " + ex);
            return 0;
        }
    }

        public Province getProvinceByProvinceId(Integer provinceId) {
        String query = "Select * from provinces where id = " + provinceId;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Province province = new Province();
            while (rs.next()) {
                province.setId(rs.getInt("id"));
                province.setName(rs.getString("name"));
                province.setCountryId(rs.getInt("country_id"));
            }
            con.close();
            return province;
        } catch (Exception ex) {
            System.out.println("error en obtener datos province " + provinceId + ": " + ex);
            return new Province();
        }
    }

        public City getCityByCityId(Integer cityId) {
        String query = "Select * from cities where id = " + cityId;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            City city = new City();
            while (rs.next()) {
                city.setId(rs.getInt("id"));
                city.setName(rs.getString("name"));
                city.setProvinceId(rs.getInt("province_id"));
            }
            con.close();
            return city;
        } catch (Exception ex) {
            System.out.println("error en obtener datos city " + cityId + ": " + ex);
            return new City();
        }
    }

    public List<Person> getPersonByCriteriaAndType(String criteria, String type) {
        String query = "Select p.*,c.* from person as p "
                + "inner join countries as c on c.id = p.nationality "
                + "where p.identification_type = '" + type + "' and p.name like '%" + criteria + "%' limit 50;";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            List<Person> persons = new ArrayList<>();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("p.id"));
                person.setAddress(rs.getString("p.address"));
                person.setCityAddress(rs.getInt("p.city_address"));
                person.setDateBirth(rs.getDate("p.date_birth"));
                person.setEmail(rs.getString("p.email"));
                person.setGender(rs.getString("p.gender"));
                person.setIdentificationNumber(rs.getString("p.identification_number"));
                person.setIdentificationType(rs.getString("p.identification_type"));
                person.setName(rs.getString("p.name"));
                person.setCountryId(rs.getInt("p.nationality"));
                person.setPhone(rs.getString("p.phone"));
                person.setNationality(rs.getString("c.name"));
                persons.add(person);
            }
            con.close();
            return persons;
        } catch (Exception ex) {
            System.out.println("error en obtener datos person por " + criteria + " y " + type + ": " + ex);
            return new ArrayList<>();
        }
    }

    public Person getPersonById(Integer id) {
        String query = "Select p.*,c.* from person as p "
                + "inner join countries as c on c.id = p.nationality "
                + "where p.id = " + id;
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            Person person = new Person();
            while (rs.next()) {
                person.setId(rs.getInt("p.id"));
                person.setAddress(rs.getString("p.address"));
                person.setCityAddress(rs.getInt("p.city_address"));
                person.setDateBirth(rs.getDate("p.date_birth"));
                person.setEmail(rs.getString("p.email"));
                person.setGender(rs.getString("p.gender"));
                person.setIdentificationNumber(rs.getString("p.identification_number"));
                person.setIdentificationType(rs.getString("p.identification_type"));
                person.setName(rs.getString("p.name"));
                person.setCountryId(rs.getInt("p.nationality"));
                person.setPhone(rs.getString("p.phone"));
                person.setNationality(rs.getString("c.name"));
                break;
            }
            con.close();
            return person;
        } catch (Exception ex) {
            System.out.println("error en obtener datos person por " + id + ": " + ex);
            return new Person();
        }
    }
    
    public Integer savePerson(Person person) {
        String sql = "INSERT INTO person ("
                + "identification_type, identification_number, name, address, "
                + "nationality, city_address, email, phone, date_birth, gender"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, person.getIdentificationType());
            ps.setString(2, person.getIdentificationNumber());
            ps.setString(3, person.getName());
            ps.setString(4, person.getAddress());

            if (person.getCountryId() != null) {
                ps.setInt(5, person.getCountryId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            if (person.getCityAddress() != null) {
                ps.setInt(6, person.getCityAddress());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            ps.setString(7, person.getEmail());
            ps.setString(8, person.getPhone());

            if (person.getDateBirth() != null) {
                ps.setDate(9, new java.sql.Date(person.getDateBirth().getTime()));
            } else {
                ps.setNull(9, java.sql.Types.DATE);
            }

            ps.setString(10, person.getGender());            

            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // devuelve el id generado
                    }
                }
            }

            return null; // no se insertó nada o no hubo id generado

        } catch (SQLException e) {
            System.err.println("No se creó la persona " + person.getName() + " con identificación " + person.getIdentificationNumber() + ": " + e);
            return null;
        }
    }
    
        public boolean updatePerson(Person person) {
        String sql = "UPDATE person SET "
                + "identification_type = ?, "
                + "identification_number = ?, "
                + "name = ?, "
                + "address = ?, "
                + "nationality = ?, "
                + "city_address = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "date_birth = ?, "
                + "gender = ? "
                + "WHERE id = ?";
        try {
            Connection con = Operations.doConnectionToFormularios();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, person.getIdentificationType());
            ps.setString(2, person.getIdentificationNumber());
            ps.setString(3, person.getName());
            ps.setString(4, person.getAddress());

            if (person.getCountryId() != null) {
                ps.setInt(5, person.getCountryId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            if (person.getCityAddress() != null) {
                ps.setInt(6, person.getCityAddress());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            ps.setString(7, person.getEmail());
            ps.setString(8, person.getPhone());

            if (person.getDateBirth() != null) {
                ps.setDate(9, new java.sql.Date(person.getDateBirth().getTime()));
            } else {
                ps.setNull(9, java.sql.Types.DATE);
            }

            ps.setString(10, person.getGender());            
            ps.setInt(11, person.getId());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception ex) {
            System.err.println("No se pudo actualizar la persona con id " + person.getId() + ": " + ex);
            return false;
        }
    }
}