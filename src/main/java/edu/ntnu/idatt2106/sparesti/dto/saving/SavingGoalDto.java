package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.*;

import java.util.Date;

@Getter
@Data
@Builder
public class SavingGoalDto {
    // user, description ?
    private String goalName;
    private Date deadLine;
    private double amount;
    private double progress;
    private boolean achieved;
    
}
