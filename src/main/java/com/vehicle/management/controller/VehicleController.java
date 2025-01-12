
package com.vehicle.management.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.vehicle.management.bean.Vehicle;
import com.vehicle.management.criteria.CriteriaExample;
import com.vehicle.management.dto.VehicleDto;
import com.vehicle.management.payload.ApiResponse;
import com.vehicle.management.service.VehicleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name="myapi")
public class VehicleController {
	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private CriteriaExample criteriaExample;

	@PostMapping("/addVehicle")
	public ApiResponse<VehicleDto> saveVehicle(@RequestBody VehicleDto vehicleDto) {
		return vehicleService.saveVehicle(vehicleDto);
	}

	@GetMapping("/getVehicle/{id}")

	public ApiResponse<VehicleDto> getVehicle(@PathVariable("id") long id) {
		return vehicleService.getVehicleById(id);
	}

	@GetMapping("/getAllVehicle")
	public ResponseEntity<List<VehicleDto>> getAllVehicle(
			@RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

		// List<Vehicle> vehicle = vehicleService.getAllVehicles();
		return new ResponseEntity<List<VehicleDto>>(vehicleService.getAllVehicles(pageNumber, pageSize), HttpStatus.OK);
	}

	@PutMapping("/updateVehicle/{id}")

	public ApiResponse<VehicleDto> updateVehicles(@PathVariable("id") long id, @RequestBody VehicleDto vehicleDto) {

		// VehicleDto updated = vehicleService.updateVehicle(id, vehicleDto);

		return vehicleService.updateVehicle(id, vehicleDto);
	}

	@DeleteMapping("/deletevehicle/{id}")
	public ApiResponse<VehicleDto> deleteVehicle(@PathVariable("id") long id) {
		vehicleService.deleteVehicle(id);
		return vehicleService.deleteVehicle(id);
	}

	@GetMapping("/getvehicletemp")
	public String getVehicleTemp() {
		// vehicleService.getVehiclebyTemplet(id);
		return vehicleService.getVehiclebyTemplet();
	}

	@GetMapping("/pdf/generate")
	public void generatePdf(HttpServletResponse response, VehicleDto vehicleDto) throws DocumentException, IOException {

		response.setContentType("application/Pdf");

		String headerkey = "Content-Disposition";
		String headerValue = "attachment; filename_.pdf";
		response.setHeader(headerkey, headerValue);

		this.vehicleService.export(response, vehicleDto);
	}

	@GetMapping("/getCriterial")
	public List<Vehicle> getAllCriteria() {
		return criteriaExample.findbyEntity();
	}

	@GetMapping("/Vehicle/{ownername}")
	public List<Vehicle> findbyownername(@PathVariable("ownername") String ownername) {
		return criteriaExample.findbyname(ownername);
	}

}
