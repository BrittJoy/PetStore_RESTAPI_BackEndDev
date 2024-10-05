package power.wash.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import power.wash.entity.Equipment;

public interface EquipmentDao extends JpaRepository<Equipment, Long> {

}
