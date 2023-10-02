package com.hruza.carrental.view;

public interface View {

    class AuthenticationResponse{}

    class Base extends AuthenticationResponse{}


    class User extends Base{}

    class Car extends Base{}

    class CarInternal extends Car{}

    class Ad extends Car{}

    class RentalCompany extends Base{}

    class Receipt extends Car{}

    class CompanyReceipt extends Receipt{}

    class CustomerReceipt extends Receipt{}

    class CustomerCar extends Car{}
}
