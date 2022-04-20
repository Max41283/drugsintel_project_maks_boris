package drugsintel.accounting.security.jwt.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import drugsintel.accounting.security.jwt.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByAccountId(Long id);

}
