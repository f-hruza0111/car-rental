import React from "react";
import { Link } from "react-router-dom";
import handleLogout from '../api/handleLogout'


function Navbar() {

  const role = sessionStorage.getItem('role')
  const loggedIn = role !== null;


  let links

  if(role === 'CUSTOMER'){
    links = 
    <>
       <li class="nav-item">
              <Link class="nav-link" to="/customer/profile">Profile</Link>
        </li>

        <li class="nav-item">
              <Link class="nav-link" to="/customer/receipts">Receipts</Link>
        </li>

        <li class="nav-item">
              <Link class="nav-link" to="/customer/rented-cars">Rented Cars</Link>
        </li>

        <li class="nav-item">
              <Link class="nav-link" onClick={() => handleLogout()}>Logout</Link>
        </li>
    </>
  } else if(role === 'RENTAL_COMPANY') {
    links = 
    <>
      <li class="nav-item">
              <Link class="nav-link" to="/rental-company/create-ad">Create ad</Link>
      </li>

      <li class="nav-item">
              <Link class="nav-link" to="/rental-company/fleet">Your fleet</Link>
      </li>

      <li class="nav-item">
              <Link class="nav-link" to="/rental-company/ads">Your ads</Link>
      </li>

      <li class="nav-item">
              <Link class="nav-link" to="/rental-company/profile">Profile</Link>
      </li>

      <li class="nav-item">
              <Link class="nav-link" to="/rental-company/receipts">Receipts</Link>
      </li>

      <li class="nav-item">
              <Link class="nav-link" onClick={() => handleLogout()}>Logout</Link>
        </li>
    </>
  }



  return(
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <Link class="navbar-brand" to="/">Car rental</Link>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          
          {role !== "RENTAL_COMPANY"  ? 
            <li class="nav-item active">
              <Link class="nav-link" to="/">Ads</Link>
            </li> 
            : null
          }
          {!loggedIn 
            ? <>

                <li class="nav-item">
                <Link class="nav-link" to="/customer/registration">Customer registration</Link>
                </li>
      
                <li class="nav-item">
                <Link class="nav-link" to="/rental-company/registration">Rental company registration</Link>
                </li>
      
                <li class="nav-item">
                <Link class="nav-link" to="/login">Login</Link>
                </li>
              </>

            : links 

          }


          
         
        </ul>
        {/* <form class="form-inline my-2 my-lg-0">
          <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form> */}
      </div>    
    </nav>
  )
}

export default Navbar;