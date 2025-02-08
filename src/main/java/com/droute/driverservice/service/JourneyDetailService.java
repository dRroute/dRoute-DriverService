package com.droute.driverservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.droute.driverservice.entity.JourneyDetailEntity;
import com.droute.driverservice.repository.JourneyDetailRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class JourneyDetailService {

    @Autowired
    private JourneyDetailRepository journeyDetailRepository;

    public JourneyDetailEntity postJourneyDetail(JourneyDetailEntity journeyDetail) {
        return journeyDetailRepository.save(journeyDetail);
    }

    public JourneyDetailEntity getJourneyDetailById(Long journeyId) {
        return journeyDetailRepository.findById(journeyId)
                .orElseThrow(() -> new EntityNotFoundException("Journey not found with given id"));
    }

    public JourneyDetailEntity updateJourneyDetailById(JourneyDetailEntity journeyDetail) {
        if (getJourneyDetailById(journeyDetail.getJourneyId()) == null) {
            throw new EntityNotFoundException("Journey not found with given id");
        }

        return journeyDetailRepository.save(journeyDetail);
    }

    public void deleteJourneyDetailById(Long journeyId) {
        JourneyDetailEntity journeyDetail = getJourneyDetailById(journeyId);
        if (journeyDetail == null) {
            throw new EntityNotFoundException("Journey not found with given id");
        }

        journeyDetailRepository.deleteById(journeyId);
    }
}
