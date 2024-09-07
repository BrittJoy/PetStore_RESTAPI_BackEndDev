package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
	
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;

}
