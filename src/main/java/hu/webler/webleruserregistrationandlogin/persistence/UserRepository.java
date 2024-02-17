package hu.webler.webleruserregistrationandlogin.persistence;

import hu.webler.webleruserregistrationandlogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
