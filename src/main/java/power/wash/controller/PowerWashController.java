package power.wash.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import power.wash.controller.model.ClientData;
import power.wash.controller.model.ClientData.EquipmentData;
import power.wash.controller.model.ClientData.EmployeeData;
import power.wash.service.PowerWashService;

@RestController
@RequestMapping("/power-wash")
@Slf4j
public class PowerWashController {
	@Autowired
	private PowerWashService powerWashService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClientData insertClient(@RequestBody ClientData clientData) {
		log.info("Creating client {}", clientData);
		return powerWashService.saveClient(clientData);
	}

	@PutMapping("/{clientId}")
	public ClientData updateClient(@PathVariable Long clientId, @RequestBody ClientData clientData) {
		clientData.setClientId(clientId);
		log.info("Update client {}", clientData);
		return powerWashService.saveClient(clientData);
	}

	@PostMapping("/{clientId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeData addEmployeeToClient(@PathVariable Long clientId,
			@RequestBody EmployeeData employeeData) {
		log.info("Adding employee {} to client with ID={}", employeeData, clientId);

		return powerWashService.saveEmployee(clientId, employeeData);
	}

	@PostMapping("/{clientId}/equipment")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EquipmentData addEquipmentToClient(@PathVariable Long clientId,
			@RequestBody EquipmentData equipmentData) {
		log.info("Adding equipment {} to client with ID={}", equipmentData, clientId);

		return powerWashService.saveEquipment(clientId, equipmentData);
	}

	@GetMapping
	public List<ClientData> retrieveAllClients() {
		log.info("Retrieving all clients");
		return powerWashService.retrieveAllClients();
	}

	@GetMapping("/{clientId}")
	public ClientData retrieveClientById(@PathVariable Long clientId) {
		log.info("Retrieving client with ID={}", clientId);
		return powerWashService.retrieveClientById(clientId);
	}

	@DeleteMapping("/{clientId}")
	public Map<String, String> deleteClientById(@PathVariable Long clientId) {
		log.info("Deleting client with ID={}", clientId);

		powerWashService.deleteClientById(clientId);

		return Map.of("message", "Client with ID=" + clientId + " deleted.");

	}

}
