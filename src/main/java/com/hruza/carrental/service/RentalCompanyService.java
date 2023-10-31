package com.hruza.carrental.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruza.carrental.entity.Car;
import com.hruza.carrental.entity.CarRentalAd;
import com.hruza.carrental.entity.RentalCompany;
import com.hruza.carrental.entity.RentalReceipt;
import com.hruza.carrental.page.JsonPage;
import com.hruza.carrental.repository.CarRentalAdRepository;
import com.hruza.carrental.repository.CarRepository;
import com.hruza.carrental.repository.RentalCompanyRepository;
import com.hruza.carrental.repository.RentalReceiptRepository;
import com.hruza.carrental.http.communication.AdRequest;
import com.hruza.carrental.http.communication.CarRequest;
import com.hruza.carrental.view.CompanyCarView;
import com.hruza.carrental.view.ReceiptView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service

public class RentalCompanyService extends AppUserService {

    private final RentalCompanyRepository repository;
    private final CarRepository carRepository;

    private final CarRentalAdRepository adRepository;

    private final RentalReceiptRepository receiptRepository;



    public RentalCompanyService(RentalCompanyRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder,
                                CarRepository carRepository, CarRentalAdRepository adRepository, RentalReceiptRepository receiptRepository) {
        super(repository, bCryptPasswordEncoder);
        this.repository = repository;
        this.carRepository = carRepository;
        this.adRepository = adRepository;
        this.receiptRepository = receiptRepository;
    }


    public RentalCompany findRentalCompanyById(Long id){
        return (RentalCompany) repository.findById(id).orElseThrow(() -> new IllegalStateException("Rental company with ID = " + id + " not found"));
    }

    public Long addCar(Car car) {
        return carRepository.save(car).getId();
    }


    public Car findOwnedCarByID(Long carID, Long rentalCompanyID) {
        RentalCompany rentalCompany = (RentalCompany) repository.findById(rentalCompanyID).orElseThrow(
                () -> new IllegalStateException("Rental company with ID = " + rentalCompanyID + " not found")
        );

        return rentalCompany.getCars()
                .stream()
                .filter((car) -> Objects.equals(car.getId(), carID))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Car with ID = " + carID + " not owned by company with ID = " + rentalCompany));
    }

    public Long createAd(CarRentalAd ad) {
        ad.getRentalCompany().getAds().add(ad);
        return adRepository.save(ad).getId();
    }




    public void update(RentalCompany rentalCompany) {
        repository.save(rentalCompany);
    }

    public List<CompanyCarView> getFleet(Long rentalCompanyID) {
      return((RentalCompany)repository
                .findById(rentalCompanyID)
                .orElseThrow(
                () -> new IllegalStateException("Rental company with ID = " + rentalCompanyID + " not found")
        )).getCars().stream()
              .map(car -> CompanyCarView.builder()
                      .id(car.getId())
                      .make(car.getMake())
                      .model(car.getModel())
                      .yearOfProduction(String.valueOf(car.getYearOfProduction()))
                      .kilometers(car.getKilometers())
                      .registeredUntil(String.valueOf(car.getRegisteredUntil()))
                      .licensePlate(car.getLicensePlate())
                      .available(car.getAvailable())
                      .build()
              ).collect(Collectors.toList());

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteCar(Long carID, Long rentalCompanyID) {
       carRepository.delete(carRepository.findById(carID).orElseThrow(() -> new IllegalStateException("Car with id = " + carID + " couldn't be deleted")));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteProfile(Long rentalCompanyID) {
        repository.deleteById(rentalCompanyID);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateCar(Long carID, Long rentalCompanyID, CarRequest data) {
        Car car = findOwnedCarByID(carID, rentalCompanyID);

        if(!car.getLicensePlate().equals(data.licensePlate()) && carRepository.existsByLicensePlate(data.licensePlate())){
            throw new IllegalStateException(("Car with licence plate " + data.licensePlate() + " alredy exists"));
        }

        car.setMake(data.make());
        car.setModel(data.model());
        car.setHorsepower(data.horsepower());
        car.setKilometers(data.kilometers());
        car.setYearOfProduction(2017);
        car.setRegisteredUntil(data.registeredUntil());
        car.setEngineType(data.engineType());
        car.setTransmissionType(data.transmissionType());
        car.setPowerDelivery(data.powerDelivery());
        car.setLicensePlate(data.licensePlate());

        carRepository.save(car);

    }

    public JsonPage<CarRentalAd> getAds(Long companyID, int page, int size, Sort sort) {

        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<CarRentalAd> ads = adRepository.findAllByRentalCompany((RentalCompany) repository.findById(companyID).orElseThrow(), pageable);
        return new JsonPage<>(ads, pageable);


    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateAd(Long companyID, Long adID, AdRequest adData) {
        Car car = findOwnedCarByID(adData.carID(), companyID);

        CarRentalAd ad = adRepository.findById(adID).orElseThrow(() -> new IllegalStateException("Ad with id =" + adID + " not found"));
        if(!ad.getRentalCompany().equals(repository.findById(companyID).get())) throw new IllegalStateException("Ad not created by company with id " + companyID);

        ad.setCar(car);
        ad.setDailyRentCost(adData.dailyRentingCost());

        adRepository.save(ad);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void extendExpiredAd(Long companyID, Long adID) {
        RentalCompany rentalCompany = (RentalCompany) repository.findById(companyID)
                .orElseThrow(() -> new IllegalStateException("Company with id = " + companyID + " not found"));

        CarRentalAd ad = rentalCompany.getAds()
                .stream()
                .filter(rentalAd -> rentalAd.getId().equals(adID))
                .findFirst().orElseThrow(() -> new IllegalStateException("Ad with id = " + adID + " not owned by company"));

        ad.setExpires(LocalDateTime.now().plusMonths(1));
        adRepository.save(ad);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteAd(Long rentalCompanyID, Long adID) {
//       List<CarRentalAd> ads = getAds(rentalCompanyID, page, size, sort);
       CarRentalAd ad = adRepository.findByIdAndRentalCompanyId(adID, rentalCompanyID).orElseThrow();

       adRepository.delete(ad);
    }

    public void getReceipts(HttpServletRequest request, HttpServletResponse response, Long rentalCompanyID) throws IOException {
       List<RentalReceipt> receipts = ((RentalCompany) repository.findById(rentalCompanyID)
                .orElseThrow(() -> new IllegalStateException("Error retrieving receipts"))
        ).getReceipts();


        List<ReceiptView> receiptViews = receipts.stream()
                .map(receipt ->
                        ReceiptView.builder()
                                .id(receipt.getId())
                                .carID(receipt.getCar().getId())
                                .car(receipt.getCar().getMake() + " " + receipt.getCar().getModel())
                                .licensePlate(receipt.getCar().getLicensePlate())
                                .rentalCompanyID(receipt.getRentalCompany().getId())
                                .rentalCompany(receipt.getRentalCompany().getName())
                                .daysRentedFor(receipt.getDaysRentedFor())
                                .customerID(receipt.getCustomer().getId())
                                .customer(receipt.getCustomer().getFirstName() + " " + receipt.getCustomer().getLastName())
                                .costPerDay(receipt.getDailyCost() + "€")
                                .rentedAt(receipt.getDateOfRental().toString())
                                .totalCost(receipt.getDailyCost() * receipt.getDaysRentedFor() + "€")
                                .returnDate(receipt.getDateOfRental().plusDays(receipt.getDaysRentedFor()).toString())
                                .build()
                ).toList();


        new ObjectMapper().writeValue(response.getOutputStream(), receiptViews);
    }

    public RentalReceipt getReceipt(Long rentalCompanyID, Long receiptID) {
        RentalCompany rentalCompany = findRentalCompanyById(rentalCompanyID);

        RentalReceipt receipt = receiptRepository.findById(receiptID).orElseThrow(() -> new IllegalStateException("Receipt with id " + receiptID + " not found"));

        if(!receipt.getRentalCompany().equals(rentalCompany)) throw new IllegalStateException("Receipt not owned by company");

        return receipt;
    }
}
