package com.axis.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.entity.Transaction;
import com.axis.entity.TransactionResponse;
import com.axis.service.UserDetailsServiceImpl;

@RestController
//@CrossOrigin(origins = "http://localhost:3000",allowCredentials="true")
@RequestMapping("/transaction/employee")
public class EmployeeController {

//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private JwtUtil jwtUtil;
//	
	@Autowired
	private UserDetailsServiceImpl service;
	
	@GetMapping("/show-all-transactions")
	public List<TransactionResponse> showAllTransactions() {
		List<Transaction> tList=service.fetchAllTransactions();
		List<TransactionResponse> trList = new ArrayList<>();
		for(Transaction t : tList) 
			trList.add(new TransactionResponse(t.getTransactionid(),t.getDatetime(),t.getAccount().getAccid(),t.getAccount().getAccno(),t.getAmount(),t.getDescription(),t.getTransactiontype()));
		return trList;
//		return null;
	}
	
	
	
//	
//	@GetMapping("/home")
//	public String home(HttpServletRequest request) {
//		String username = request.getAttribute("username").toString();
//		return "Hello "+username+"(employee) welcome home.\nYou're logged in.";
//	}
//	
//	@PostMapping("/authenticate")
//	public String authenticate(@RequestBody AuthRequest authRequest) throws Exception{
//		System.out.println("Request by employee..................."+authRequest.getUsername());
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//		}catch(Exception ex) {
//			throw new Exception("Invalid username/password...............................");
//		}
//		return jwtUtil.generateToken(authRequest.getUsername());
//	}
//	
//	@GetMapping("/profile")
//	public String profile(HttpServletRequest request) {
//		String username=request.getAttribute("username").toString();
//		Users user=service.viewProfile(username);
//		return user.toString();
//	}
//	
//	@GetMapping("/pending-accounts")
//	public List<Account> viewAllPendingAccounts() {
//		return service.viewAllPendingAccounts();
//	}
//	
//	@PutMapping("/activate-account")
//	public String activateAccount(@RequestBody Map<String, Object> requestData) {
//		int accountid=Integer.valueOf(requestData.get("accid").toString());
//		service.activateAccount(accountid);
//		return "Account is activated successfully";
//	}
//	
//	@GetMapping("/all-accounts")
//	public List<Account> viewAllAccounts() {
//		return service.listAllAccounts();
//	}
//	
//	@DeleteMapping("/delete-customer")
//	public String deleteCustomer(@RequestBody Map<String, Object> requestData) {
//		String accno=String.valueOf(requestData.get("accno").toString());
//		service.deleteUserAndAccount(accno);
//		return "Customer is deleted successfully";
//	}
}
