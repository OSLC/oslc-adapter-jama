package com.jama.oslc.web;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.lyo.server.oauth.consumerstore.FileSystemConsumerStore;
import org.eclipse.lyo.server.oauth.core.AuthenticationException;
import org.eclipse.lyo.server.oauth.core.consumer.ConsumerStore;
import org.eclipse.lyo.server.oauth.core.consumer.ConsumerStoreException;
import org.eclipse.lyo.server.oauth.core.consumer.LyoOAuthConsumer;
import org.eclipse.lyo.server.oauth.core.utils.AbstractAdapterCredentialsFilter;
import org.eclipse.lyo.server.oauth.core.utils.UnauthorizedException;

public class JamaCredentialsFilter extends AbstractAdapterCredentialsFilter2 {

	protected JamaCredentialsFilter() {
		super("Jama", "Jama");
	}

	@Override
	protected Credentials getCredentialsFromRequest(HttpServletRequest request) {
		try {
			Credentials credentials = HttpUtils.getCredentials(request);
			if (credentials == null) {
				throw new com.jama.oslc.web.UnauthorizedException();
			}
			return credentials;
		} catch (com.jama.oslc.web.UnauthorizedException e) {
			;
		}
		return null;
	}

	@Override
	protected Object getCredentialsForOAuth(String id, String password) {
		if (id == OAUTH_EMPTY_TOKEN_KEY) {
			Credentials result = new Credentials();
			result.setUsername(AdapterInitializer.admin);
			result.setPassword(AdapterInitializer.admin_password);
			return result;
		}
		Credentials result = new Credentials();
		result.setUsername(id);
		result.setPassword(password);
		return result;
	}

	@Override
	protected Object login(Object credentialsObject, HttpServletRequest request)
			throws com.jama.oslc.web.UnauthorizedException {

		if (credentialsObject instanceof Credentials) {
			Credentials credentials = (Credentials) credentialsObject;
			try {
				FileSystemConsumerStore fileSystemConsumerStore = new FileSystemConsumerStore("jamaOAuthStore.xml");
				LyoOAuthConsumer consumer = fileSystemConsumerStore.getConsumer(credentials.getUsername());
				if (consumer == null) {
					throw new com.jama.oslc.web.UnauthorizedException();
				}
			} catch (ConsumerStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new com.jama.oslc.web.UnauthorizedException();
		}

		return null;
	}

	@Override
	protected boolean isAdminSession(String id, Object session, HttpServletRequest request) {
		return AdapterInitializer.admin != null && AdapterInitializer.admin.equals(id);
	}

	@Override
	protected ConsumerStore createConsumerStore() throws Exception {
		return new FileSystemConsumerStore("jamaOAuthStore.xml");
	}

}
