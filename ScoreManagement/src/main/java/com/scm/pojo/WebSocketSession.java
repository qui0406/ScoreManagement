package com.scm.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "websocket_session")
public class WebSocketSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "websocket_session_id")
    private String websocketSessionId;

    @Column(name= "user_id")
    private String userId;

    private LocalDateTime timestamp;
}
