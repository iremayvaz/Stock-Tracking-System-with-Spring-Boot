package model.entity;

import java.util.Date;

public class RefreshToken {
    private Long id;
    private String refreshToken;
    private Date expireDate;
    private User user;
}
