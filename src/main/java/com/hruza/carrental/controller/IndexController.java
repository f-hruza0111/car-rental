package com.hruza.carrental.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.entity.CarRentalAd;
import com.hruza.carrental.entity.RentalCompany;
import com.hruza.carrental.http.communication.AuthenticationRequest;
import com.hruza.carrental.http.communication.AuthenticationResponse;
import com.hruza.carrental.service.AuthenticationService;
import com.hruza.carrental.service.CarRentalAdService;
import com.hruza.carrental.service.RentalCompanyService;
import com.hruza.carrental.view.View;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/car-rental")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class IndexController {



    private final CarRentalAdService carRentalAdService;
    private final RentalCompanyService rentalCompanyService;
    private final AuthenticationService authenticationService;



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,  HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }




    @GetMapping("/ads")
    @JsonView(View.Ad.class)
    public Page<CarRentalAd> getAds(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "1") int size,
                                    @RequestParam(required = false, defaultValue = "id") String sortCriteria,
                                    @RequestParam(required = false, defaultValue = "0") Integer sortOrder){
        Sort sort;


        if(sortOrder == 1) sort = Sort.by(sortCriteria).descending();
        else sort = Sort.by(sortCriteria).ascending();



        return carRentalAdService.findAll(page, size, sort);
    }

    @GetMapping("/ads/{adID}")
    @JsonView(View.Ad.class)
    public CarRentalAd findRentalAd(@PathVariable Long adID){
        return carRentalAdService.findAdByID(adID);
    }

    @GetMapping("/company-profile/{companyID}")
    @JsonView(View.RentalCompany.class)
    public RentalCompany getRentalCompanyProfile(@PathVariable Long companyID){
        return rentalCompanyService.findRentalCompanyById(companyID);
    }

    @GetMapping("/company-profile/{companyID}/ads")
    @JsonView(View.RentalCompany.class)
    public List<CarRentalAd> getRentalCompanyAds(@PathVariable Long companyID){
        return rentalCompanyService.getAds(companyID);
    }

}
