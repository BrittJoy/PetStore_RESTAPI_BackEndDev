package power.wash.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import power.wash.entity.Client;

public interface ClientDao extends JpaRepository<Client, Long> {

}
