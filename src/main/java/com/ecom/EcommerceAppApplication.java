package com.ecom;

import com.ecom.config.AppConstants;
import com.ecom.entities.MasSerial;
import com.ecom.entities.Role;
import com.ecom.entities.master_entity.MasSerialPK;
import com.ecom.repositories.MasSerialRepo;
import com.ecom.repositories.RoleRepo;
import com.ecom.services.MasSrlService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class EcommerceAppApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private MasSerialRepo masSerialRepo;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppApplication.class, args);
		System.out.println("ECommerce Project is Started....");
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(this.passwordEncoder.encode("mi2"));

		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.SELLER_USER);
			role1.setName("ROLE_SELLER");

			Role role2 = new Role();
			role2.setId(AppConstants.CUSTOMER_USER);
			role2.setName("ROLE_CUSTOMER");

			List<Role> roles = List.of(role, role1, role2);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach((r)->{
				System.out.println(r.getName());
			});
		}catch (Exception e){
			e.printStackTrace();
		}
		//Run First tine when it is deployed first time in server  ***VVI***
		/*try {
			//Category Mas Serial No Create
			MasSerial categorySerial=new MasSerial();
			categorySerial.setYear(2023);
			categorySerial.setSerialType("CATSRL");
			categorySerial.setSerialDesc("Category Serial");
			//categorySerial.setSerialNo(0);
			categorySerial.setEntryBy("NIC");
			categorySerial.setEntryDate(new Timestamp(new Date().getTime()));
			categorySerial.setStatus("A");

			//Product Mas Serial No Create
			MasSerial productSerial=new MasSerial();
			productSerial.setYear(2023);
			productSerial.setSerialType("PROSRL");
			productSerial.setSerialDesc("Product Serial");
			//productSerial.setSerialNo(0);
			productSerial.setEntryBy("NIC");
			productSerial.setEntryDate(new Timestamp(new Date().getTime()));
			productSerial.setStatus("A");
			//Save Serial no
			List<MasSerial> masSerials = List.of(categorySerial,productSerial);
			List<MasSerial> masSerials1 = this.masSerialRepo.saveAll(masSerials);
			masSerials1.forEach((r)->{
				System.out.println("Serial Type : "+r.getSerialType() + "Serial No : "+r.getSerialNo());
			});
		}catch (Exception e){
			e.printStackTrace();
		}*/
	}

}
