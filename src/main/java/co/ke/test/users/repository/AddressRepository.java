package co.ke.test.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.ke.test.users.entities.Address;
import co.ke.test.users.entities.Users;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	List<Address> findByUser(Users user);

}
