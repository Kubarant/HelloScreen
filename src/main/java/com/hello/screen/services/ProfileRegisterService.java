package com.hello.screen.services;

import com.hello.screen.datacollectors.news.PreferredNewsSaver;
import com.hello.screen.model.News;
import com.hello.screen.model.Product;
import com.hello.screen.model.Profile;
import com.hello.screen.model.ProfileRegistrationDTO;
import com.hello.screen.repository.ProductRepository;
import com.hello.screen.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileRegisterService {

    private ProfileRepository profileRepository;
    private PreferredNewsSaver preferredNewsSaver;
    private ProductChoosingService productChoosingService;
    private ProductRepository productRepository;

    @Autowired
    public ProfileRegisterService(ProfileRepository profileRepository, PreferredNewsSaver preferredNewsSaver, ProductChoosingService productChoosingService, ProductRepository productRepository) {
        this.profileRepository = profileRepository;
        this.preferredNewsSaver = preferredNewsSaver;
        this.productChoosingService = productChoosingService;
        this.productRepository = productRepository;
    }

    public void registerNewUser(ProfileRegistrationDTO registrationDTO) {
        Profile profile = registrationDtoToProfile(registrationDTO);
        profileRepository.save(profile)
                .subscribe();
    }

    public Profile registrationDtoToProfile(ProfileRegistrationDTO registrationDTO) {
        Profile profile = new Profile();
        profile.setKeywords(registrationDTO.getKeywords());
        profile.setName(registrationDTO.getName());
        profile.setPreferredCategories(registrationDTO.getPreferences());
        return setFirstPreferredStuff(profile);
    }

    public Profile setFirstPreferredStuff(Profile profile) {
        Profile result = profile;
        List<News> preferredNews = preferredNewsSaver.fitNewsToProfile(result);
        List<Product> products = productRepository.findAll()
                .collectList()
                .block();
        List<Product> prefferedProducts = productChoosingService.filterPrefferedProducts(products, profile.getKeywords());
        result.setProducts(prefferedProducts);
        result.setNews(preferredNews);
        return result;
    }

}
