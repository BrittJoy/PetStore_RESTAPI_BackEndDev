package power.wash.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import power.wash.controller.model.ClientData;
import power.wash.controller.model.ClientData.EquipmentData;
import power.wash.controller.model.ClientData.EmployeeData;
import power.wash.dao.EquipmentDao;
import power.wash.dao.EmployeeDao;
import power.wash.dao.ClientDao;
import power.wash.entity.Equipment;
import power.wash.entity.Employee;
import power.wash.entity.Client;


@Service
public class PowerWashService {

	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EquipmentDao equipmentDao;

	@Transactional(readOnly = false)
	public ClientData saveClient(ClientData clientData) {
		Long clientId = clientData.getClientId();
		Client client = findOrCreateClient(clientId);

		copyClientFields(client, clientData);
		return new ClientData(clientDao.save(client));

	}

	private void copyClientFields(Client client, ClientData clientData) {
		client.setClientId(clientData.getClientId());
		client.setClientName(clientData.getClientName());
		client.setClientAddress(clientData.getClientAddress());
		client.setClientCity(clientData.getClientCity());
		client.setClientState(clientData.getClientState());
		client.setClientZip(clientData.getClientZip());
		client.setClientPhone(clientData.getClientPhone());
	}

	private Client findOrCreateClient(Long clientId) {
		if (Objects.isNull(clientId)) {
			return new Client();
		} else {
			return findClientById(clientId);
		}

	}

	private Client findClientById(Long clientId) {
		return clientDao.findById(clientId)
				.orElseThrow(() -> new NoSuchElementException("Client with ID=" + clientId + " was not found."));

	}

	@Transactional(readOnly = false)
	public EmployeeData saveEmployee(Long clientId, EmployeeData employeeData) {
		Client client = findClientById(clientId);
		Long employeeId = employeeData.getEmployeeId();
		Employee employee = findOrCreateEmployee(clientId, employeeId);
		
		copyEmployeeFields(employee, employeeData);
	
		employee.setClient(client);
		client.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new EmployeeData(dbEmployee);
	
	}

	private void copyEmployeeFields(Employee employee, EmployeeData employeeData) {
		employee.setEmployeeId(employeeData.getEmployeeId());
		employee.setEmployeeName(employeeData.getEmployeeName());
		employee.setEmployeePhone(employeeData.getEmployeePhone());
		employee.setEmployeeJobTitle(employeeData.getEmployeeJobTitle());
		}
	
	private void copyEquipmentFields(Equipment equipment, EquipmentData equipmentData) {
		equipment.setEquipmentId(equipmentData.getEquipmentId());
		equipment.setEquipmentName(equipmentData.getEquipmentName());
	}
	
	
	private Employee findOrCreateEmployee(Long clientId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		}
		
		return findEmployeeById(clientId, employeeId);
	}
	
	
	private Equipment findOrCreateEquipment(Long clientId, Long equipmentId) {
		if(Objects.isNull(equipmentId)) {
			return new Equipment();
		}
		
		return findEquipmentById(clientId, equipmentId);
	}
	
	private Employee findEmployeeById(Long clientId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
		
		if(employee.getClient().getClientId() != clientId) {
			throw new IllegalArgumentException("The employee with ID=" + employeeId + " is not employed by the client with ID=" + clientId + ".");
		}
		
		return employee;
	}
	
	private Equipment findEquipmentById(Long clientId, Long equipmentId) {
		Equipment equipment = equipmentDao.findById(equipmentId)
				.orElseThrow(() -> new NoSuchElementException("Equipment with ID=" + equipmentId + " was not found."));
		
		boolean found = false;
		
		for(Client client : equipment.getClients()) {
			if(client.getClientId() == clientId) {
				found = true;
				break;
			}
		}
		
		if(!found) {
			throw new IllegalArgumentException("The equipment with ID=" + equipmentId + " is not tied to a client with ID=" + clientId);
		}
		
		return equipment;
	}
	
	
	@Transactional(readOnly = true)
	public List<ClientData> retrieveAllClients() {
		List<Client> clients = clientDao.findAll();
		
		List<ClientData> result = new LinkedList<>();
		
		for(Client client : clients) {
			ClientData cd = new ClientData(client);
			
			cd.getEquipments().clear();
			cd.getEmployees().clear();
			
			result.add(cd);
		}
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public ClientData retrieveClientById(Long clientId) {
		return new ClientData(findClientById(clientId));
	}
	
	@Transactional(readOnly = false)
	public void deleteClientById(Long clientId) {
		Client client = findClientById(clientId);
		clientDao.delete(client);
	}

	@Transactional
	public EquipmentData saveEquipment(Long clientId, EquipmentData equipmentData) {
		Client client = findClientById(clientId);
		Long equipmentId = equipmentData.getEquipmentId();
		Equipment equipment = findOrCreateEquipment(clientId, equipmentId);
		
		copyEquipmentFields(equipment, equipmentData);
		
		equipment.getClients().add(client);
		client.getEquipments().add(equipment);
		
		Equipment dbEquipment = equipmentDao.save(equipment);
		
		return new EquipmentData(dbEquipment);
		
		
	}
	
}
