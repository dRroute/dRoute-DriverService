package com.droute.driverservice.dto.response;

import com.droute.driverservice.entity.JourneyDetailEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilteredJourneyDetailsResponseDto {

    private CompleteDriverDetailsResponseDto driver;
    private JourneyDetailEntity journey;
    private Float averageDriverRating;
    
}
