package br.com.developed.ideal.atendimento.core.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfiguration {
	
	@Autowired
	private JwtKeyStoreProperties keyStoreProperties;
	
	@Bean
	KeyStore keyStore() {
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStoreProperties.getKeystoreLocation());
			keyStore.load(resourceAsStream, keyStoreProperties.getKeystorePassword().toCharArray());
			return keyStore;
		} catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
			//log.error("Unable to load keystore: {}", keyStorePath, e);
			e.printStackTrace();
		}
		
		throw new IllegalArgumentException("Unable to load keystore");
	}
	
	@Bean
	RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
		try {
			Key key = keyStore.getKey(keyStoreProperties.getKeyAlias(),  keyStoreProperties.getPrivateKeyPassphrase().toCharArray());
			if (key instanceof RSAPrivateKey) {
				return (RSAPrivateKey) key;
			}
		} catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
			//log.error("Unable to load private key from keystore: {}", keyStorePath, e);
			e.printStackTrace();
		}
		
		throw new IllegalArgumentException("Unable to load private key");
	}
	
	@Bean
	RSAPublicKey jwtValidationKey(KeyStore keyStore) {
		try {
			Certificate certificate = keyStore.getCertificate(keyStoreProperties.getKeyAlias());
			PublicKey publicKey = certificate.getPublicKey();
			
			if (publicKey instanceof RSAPublicKey) {
				return (RSAPublicKey) publicKey;
			}
		} catch (KeyStoreException e) {
			//log.error("Unable to load private key from keystore: {}", keyStorePath, e);
			e.printStackTrace();
		}
		
		throw new IllegalArgumentException("Unable to load RSA public key");
	}
	
	@Bean
	JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
		return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
	}

}
