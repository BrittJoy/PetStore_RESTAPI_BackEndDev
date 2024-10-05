package power.wash.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import power.wash.entity.Equipment;
import power.wash.entity.Employee;
import power.wash.entity.Client;


@Data
@NoArgsConstructor
public class ClientData {
	private Long clientId;
	private String clientName;
	private String clientAddress;
	private String clientCity;
	private String clientState;
	private String clientZip;
	private String clientPhone;
	private Set<EquipmentData> equipments = new HashSet<>();
	private Set<EmployeeData> employees = new HashSet<>();
	
	public ClientData(Client client) {
		clientId = client.getClientId();
		clientAddress = client.getClientAddress();
		clientCity = client.getClientCity();
		clientState = client.getClientState();
		clientZip = client.getClientZip();
		clientPhone = client.getClientPhone();
		
		for(Equipment equipment : client.getEquipments()) {
			equipments.add(new EquipmentData(equipment));
		}
		
		for(Employee employee : client.getEmployees()) {
			employees.add(new EmployeeData(employee));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class EquipmentData {
		private Long equipmentId;
		private String equipmentName;
		
		public EquipmentData(Equipment equipment) {
			equipmentId = equipment.getEquipmentId();
			equipmentName = equipment.getEquipmentName();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class EmployeeData {
		private Long employeeId;
		private String employeeName;
		private String employeePhone;
		private String employeeJobTitle;
		
		
		public EmployeeData (Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeName = employee.getEmployeeName();
			employeePhone = employee.getEmployeePhone();
			employeeJobTitle = employee.getEmployeeJobTitle();
		}
	}
	
}
