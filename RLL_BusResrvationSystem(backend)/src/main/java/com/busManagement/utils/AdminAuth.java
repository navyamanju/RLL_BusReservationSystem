package com.busManagement.utils;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data @NoArgsConstructor @AllArgsConstructor
public class AdminAuth {

	private Integer adminId;
	private String password;

}
