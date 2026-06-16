package co.ke.test.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.ke.test.users.entities.Users;
import co.ke.test.users.enums.Roles;
import co.ke.test.users.enums.Status;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

	Page<Users> findByStatusOrderByUserIdDesc(Status status, Pageable pageable);

	Page<Users> findByStatusAndRoleOrderByUserIdDesc(Status status, Roles role, Pageable pageable);
	@Query("SELECT u FROM Users u WHERE u.status=:status AND u.role=:role AND "
	        + "( LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm,'%')) OR "
	        + "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm,'%')) OR "
	        + "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm,'%')) OR "
	        + "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :searchTerm,'%'))"  
	        + ")")
	Page<Users> getUsersbyStatusRoles(@Param("status") Status status, @Param("role") Roles role, @Param("searchTerm") String searchTerm, Pageable pageable);

	@Query("SELECT u FROM Users u WHERE u.status=:status AND "
	        + "( LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm,'%')) OR "
	        + "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm,'%')) OR "
	        + "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm,'%')) OR "
	        + "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :searchTerm,'%'))" 
	        + ")")
	Page<Users> getUsersbyStatus(@Param("status") Status status, @Param("searchTerm") String searchTerm, Pageable pageable);

}
