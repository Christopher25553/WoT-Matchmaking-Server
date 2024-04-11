package org.cteichert.server.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

@Data
public abstract class Tank {
    @Id
    private String tankId;
    @NotBlank
    @JsonProperty("name")
    private String name;
    @NotBlank
    @JsonProperty("power")
    private int power;
    @NotBlank
    @JsonProperty("level")
    private int level;
    @NotBlank
    @JsonProperty("type")
    private TankType type;

    public Tank(String tankId, String name, int power, int level, TankType type) {
        this.tankId = tankId;
        this.name = name;
        this.power = power;
        this.level = level;
        this.type = type;
    }
}
