package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordDto {
	@NotBlank(message = "Không được để trống")
	private String password;
	@NotBlank(message = "Không được để trống")
    @Size(min=6,message = "Yêu cầu mật khẩu từ 6 kí tự trở lên")
	private String newPassword;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public PasswordDto(@NotBlank(message = "Không được để trống") String password,
			@NotBlank(message = "Không được để trống") @Size(min = 5, message = "Yêu cầu mật khẩu từ 5 kí tự trở lên") String newPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
	}
	public PasswordDto() {
		super();
	}
	
}
