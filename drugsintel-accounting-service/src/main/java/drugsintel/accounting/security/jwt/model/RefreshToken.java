package drugsintel.accounting.security.jwt.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import drugsintel.accounting.model.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "refresh", schema = "sign")
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Account account;
	@Column(nullable = false, unique = true)
	private String token;
	@Column(nullable = false)
	private Instant expiryDate;

}
