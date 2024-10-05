package power.wash.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD:src/main/java/pet/store/entity/PetStore.java
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
=======
	private Long clientId;
	
	private String clientName;
	private String clientAddress;
	private String clientCity;
	private String clientState;
	private String clientZip;
	private String clientPhone;
>>>>>>> 4f7937385f7c6cd184e143d43d382cd1a9bcdac7:src/main/java/power/wash/entity/Client.java

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
<<<<<<< HEAD:src/main/java/pet/store/entity/PetStore.java
	@JoinTable(name = "pet_store_customer", 
	joinColumns = @JoinColumn(name = "pet_store_id"), 
	inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>();
=======
	@JoinTable(name = "client_equipment", 
	joinColumns = @JoinColumn(name = "client_id"), 
	inverseJoinColumns = @JoinColumn(name = "equipment_id"))
	private Set<Equipment> equipments = new HashSet<>();
>>>>>>> 4f7937385f7c6cd184e143d43d382cd1a9bcdac7:src/main/java/power/wash/entity/Client.java

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();

}
