package com.saravanank.ecommerce.transaction.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.saravanank.ecommerce.transaction.model.Json;
import com.saravanank.ecommerce.transaction.model.RestResponse;

public class AuthFilter extends OncePerRequestFilter {

	private String client_id = "e-commerce-site.TJQfmiEHV1ibmQ2nz0BsZ9Td44I1SZ5V";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String internalHeader = request.getHeader("X-Internal-Request");
		System.out.println(authHeader + " " + internalHeader);
		if (authHeader == null && internalHeader == null) {
			RestResponse errResponse = new RestResponse();
			logger.error("Auth header is not found");
			errResponse.setError("Auth header is not found");
			errResponse.setMessage("Authorization header is required to access resources");
			errResponse.setStatus(401);
			errResponse.setTimestamp(new Date().toString());
			String responseContent = Json.stringify(Json.toJson(errResponse));
			response.setContentType("application/json");
			response.setStatus(401);
			response.getWriter().print(responseContent);
			return;
		}
		if (internalHeader != null) {
			String decodeHeader = new String(Base64.getDecoder().decode(internalHeader));
			if (decodeHeader.equals(client_id)) {
				filterChain.doFilter(request, response);
			} else {
				sendForbiddenResponse(response);
				return;
			}
		} else {
			String[] chunks = authHeader.split(" ")[1].split("\\.");
			Base64.Decoder jwtDecoder = Base64.getUrlDecoder();
			String payload = new String(jwtDecoder.decode(chunks[1]));
			JsonNode sub = Json.parse(payload).get("sub");
			if (sub == null) {
				RestResponse errResponse = new RestResponse();
				logger.error("Invalid authorization");
				errResponse.setError("Invalid authorization");
				errResponse.setMessage("Authorization is invalid");
				errResponse.setStatus(401);
				errResponse.setTimestamp(new Date().toString());
				String responseContent = Json.stringify(Json.toJson(errResponse));
				response.setContentType("application/json");
				response.setStatus(401);
				response.getWriter().print(responseContent);
				return;
			}
			if (!sub.asText().equals(client_id)) {
				sendForbiddenResponse(response);
				return;
			}
			filterChain.doFilter(request, response);
		}
	}

	private void sendForbiddenResponse(HttpServletResponse response) throws IOException {
		RestResponse errResponse = new RestResponse();
		logger.error("Forbidden access");
		errResponse.setError("Forbidden access");
		errResponse.setMessage("Invalid token, cannot access this endpoint with this token");
		errResponse.setStatus(403);
		errResponse.setTimestamp(new Date().toString());
		String responseContent = Json.stringify(Json.toJson(errResponse));
		response.setContentType("application/json");
		response.setStatus(403);
		response.getWriter().print(responseContent);
		return;
	}

}
