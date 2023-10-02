import React, { useState, useEffect } from 'react'
import Home from './components/Home'
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



function App() {
  return(
    <div>
      
      <BrowserRouter>
        <Routes>
          <Route index element={<Home/>}/>
          <Route path='/customer/registration' element={<CustomerRegistration/>}/>
          <Route path='/rental-company/registration' element={<RentalCompanyRegistration/>}/>
          <Route path='/login' element={<Login/>}/>


          <Route path='/customer/profile' element={<CustomerProfile/>}/>
          <Route path='/customer/receipts' element={<Receipts/>}/>
          {/* <Route path='/customer/rented-cars' element={<Cars/>}/> */}


          <Route path='/rental-company/profile' element={<CustomerProfile/>}/>
          <Route path='/rental-company/receipts' element={<Receipts/>}/>
          <Route path='/rental-company/fleet' element={<Fleet/>}/>
          <Route path='/rental-company/add-car' element={<AddCar/>}/>
          
        </Routes>
      </BrowserRouter>
      
    </div>
  ) 
}

export default App
