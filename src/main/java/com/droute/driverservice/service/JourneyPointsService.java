package com.droute.driverservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droute.driverservice.dto.request.JourneyDetailsRequestDto;
import com.droute.driverservice.entity.JourneyDetailEntity;
import com.droute.driverservice.entity.JourneyPoints;
import com.droute.driverservice.repository.JourneyPointsRepository;
import com.droute.driverservice.utils.InterPolationUtil;
import com.droute.driverservice.utils.LocationPoint;

@Service
public class JourneyPointsService {

    @Autowired
    private JourneyDetailService journeyDetailService;
    @Autowired
    private GeoCodingService geoCodingService;

    @Autowired
    private JourneyPointsRepository journeyPointsRepository;

    public JourneyDetailEntity saveJourneyAndPoints(JourneyDetailsRequestDto journeyDetail) {
        var journey = journeyDetailService.postJourneyDetail(journeyDetail);
        List<LocationPoint> interpolatedPoints = InterPolationUtil.interpolatePoints(
                Double.parseDouble(journey.getJourneySource().getLatitude() ) ,   Double.parseDouble(journey.getJourneySource().getLongitude()),
                Double.parseDouble(journey.getJourneyDestination().getLatitude()),   Double.parseDouble(journey.getJourneyDestination().getLongitude()),
                30);
        List<JourneyPoints> dbPoints = new ArrayList<>();

        for (LocationPoint p : interpolatedPoints) {
            String state = geoCodingService.getStateFromCoordinates(p.getLatitude(), p.getLongitude());
            JourneyPoints jp = new JourneyPoints(-1L,journey.getJourneyId(), p.getLatitude(), p.getLongitude(), state);
            dbPoints.add(jp);
        }

        journeyPointsRepository.saveAll(dbPoints);

        return journey;
    }

}
