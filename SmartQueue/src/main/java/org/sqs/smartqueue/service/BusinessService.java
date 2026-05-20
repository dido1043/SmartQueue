package org.sqs.smartqueue.service;

import lombok.AllArgsConstructor;
import org.sqs.smartqueue.data.dto.BusinessDto;
import org.sqs.smartqueue.data.model.Business;
import org.sqs.smartqueue.data.model.User;
import org.sqs.smartqueue.messages.Message;
import org.sqs.smartqueue.repository.BusinessRepository;
import org.sqs.smartqueue.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class BusinessService {
    private BusinessRepository businessRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public BusinessDto findByName(String name) {
        return businessRepository.findByName(name)
                .map(business -> modelMapper.map(business, BusinessDto.class))
                .orElse(null);
    }

    public BusinessDto create(BusinessDto businessDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

        Business business = modelMapper.map(businessDto, Business.class);
        business.setOwner(owner);
        businessRepository.save(business);
        return modelMapper.map(business, BusinessDto.class);
    }
    public BusinessDto update(Long id, BusinessDto businessDto) {
        Business business = businessRepository.findById(id).orElse(null);
        business.setName(businessDto.getName());
        business.setActivity(businessDto.getActivity());
        business.setRating(businessDto.getRating());
        business.setAvailableDates(businessDto.getAvailableDates());;
        businessRepository.save(business);
        return modelMapper.map(business, BusinessDto.class);
    }
    public String delete(Long id) {
        Business business = businessRepository.findById(id).orElse(null);
        if (business == null) {
            return Message.BUSINESS_NOT_FOUND;
        }
        businessRepository.delete(business);
        return Message.BUSINESS_DELETED_SUCCESSFULLY;
    }
    public BusinessDto addAvailableHours(Long id, LocalDateTime availableDates) {
        Business business = businessRepository.findById(id).orElse(null);
        if (business == null) {
            throw new IllegalStateException(Message.BUSINESS_NOT_FOUND);
        }
        if (business.getAvailableDates() == null) {
            business.setAvailableDates(new ArrayList<>());
        }
        business.getAvailableDates().add(availableDates);
        businessRepository.save(business);
        return modelMapper.map(business, BusinessDto.class);
    }
}
