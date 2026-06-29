package vs.fitnessOnline;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.Resource;
import vs.fitnessOnline.service.SlikaService;

@SpringBootApplication
@EnableScheduling
public class FitnessOnlineApplication implements CommandLineRunner {

	@Resource
	SlikaService slikaService;

	public static void main(String[] args) {
		SpringApplication.run(FitnessOnlineApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... arg) throws Exception {
		slikaService.init();
	}

}
