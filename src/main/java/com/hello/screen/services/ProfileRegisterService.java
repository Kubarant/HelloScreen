package com.hello.screen.services;

import com.hello.screen.model.Profile;
import com.hello.screen.model.ProfileRegistrationDTO;
import com.hello.screen.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileRegisterService {

    @Autowired
    private
    ProfileRepository profileRepository;

    public void register(ProfileRegistrationDTO registrationDTO) {
        Profile profile = registrationDtoToProfile(registrationDTO);
        profileRepository.save(profile)
                .subscribe();
    }

    public Profile registrationDtoToProfile(ProfileRegistrationDTO registrationDTO) {
        Profile profile = new Profile();
        profile.setKeywords(registrationDTO.getKeywords());
        profile.setName(registrationDTO.getName());
        profile.setPrefferedCategories(registrationDTO.getPreferences());
        return profile;
    }

}
