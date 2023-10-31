import React, { useState, useEffect } from 'react'
import Ads from './components/Ads'
import {BrowserRouter, Route, Routes } from 'react-router-dom'
import CustomerRegistration from './components/CustomerRegistration'
import RentalCompanyRegistration from './components/RentalCompanyRegistration'
import Login from './components/Login'
import CustomerProfile from './components/customer/CustomerProfile'
import RentedCars from './components/customer/RentedCars'
import Receipts from './components/Receipts'
import Fleet from './components/rental-company/Fleet'
import AddCar from './components/rental-company/AddCar'
import Navbar from './components/Navbar'
import Car from './components/Car'
import RentalCompanyDetails from './components/RentalCompanyDetails'
import CreateAd from './components/rental-company/CreateAd'


function App() {
  return(
    <div>
      <BrowserRouter>
        <div>
          <Navbar></Navbar>
          <Routes>
          <Route index element={<Ads pageProp = '0' adsPerPageProp ='9'/>}/>
          <Route path='/customer/registration' element={<CustomerRegistration/>}/>
          <Route path='/rental-company/registration' element={<RentalCompanyRegistration/>}/>
          <Route path='/login' element={<Login/>}/>
          <Route path='/company/details' element={<RentalCompanyDetails/>}/>
          <Route path='/company/details/:id/ads' element={<Ads url = '/company/details/' pageProp = '0' adsPerPageProp ='9'/>}/>


          
          <Route path='/car/details' element={<Car/>}/>


          <Route path='/customer/profile' element={<CustomerProfile/>}/>
          <Route path='/customer/receipts' element={<Receipts/>}/>
          <Route path='/customer/rented-cars' element={<RentedCars/>}/>


          <Route path='/rental-company/profile' element={<RentalCompanyDetails/>}/>
          <Route path='/rental-company/receipts' element={<Receipts/>}/>
          <Route path='/rental-company/fleet' element={<Fleet/>}/>
          <Route path='/rental-company/add-car' element={<AddCar/>}/>
          <Route path='/rental-company/create-ad' element={<CreateAd/>}/>
          
        </Routes>
        </div>
       
      </BrowserRouter>
    </div>
  ) 
}

export default App
