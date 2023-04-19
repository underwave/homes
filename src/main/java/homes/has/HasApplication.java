package homes.has;

import homes.has.domain.Category;
import homes.has.domain.Member;
import homes.has.domain.Post;
import homes.has.service.MemberService;
import homes.has.service.PostService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HasApplication.class, args);
	}

}
