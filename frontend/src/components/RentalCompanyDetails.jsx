
import { useSearchParams} from "react-router-dom"
import { useState, useEffect } from "react"
import axios from "../api/axios"
import { Link } from "react-router-dom"


function RentalCompanyDetails() {
    const [searchParams, setSearchParams] = useSearchParams()
   
    const id = searchParams.get('id')
    

    const url = `/company/details/${id}`

    const[profile, setProfile] = useState([])

    const fetchCompanyProfile = async () => {
        const res = await axios.get(url);
 
        setProfile(res.data);
        
        
    
      }
    
    
      useEffect(() => {
         fetchCompanyProfile()
      }, []);



     
      
      return(

        
           <div className="container">
                <div class="col-lg-12 mb-4 mb-sm-5">
                    <div class="card card-style1 border-0">
                        <div class="card-body p-1-9 p-sm-2-3 p-md-6 p-lg-7">
                            <div class="row align-items-center">
                                <div class="col-lg-6 mb-4 mb-lg-0">
                                    <img src={profile.logoPictureLink} alt="..."/>
                                </div>
                                <div class="col-lg-6 px-xl-10">
                                    <div class="bg-secondary d-lg-inline-block py-1-9 px-1-9 px-sm-6 mb-1-9 rounded">
                                        <h3 class="h2 text-white mb-0">{profile.name}</h3>
                                
                                    </div>
                                    <ul class="list-unstyled mb-1-9">
                                        <li class="mb-2 mb-xl-3 display-28"><span class="display-26 text-secondary me-2 font-weight-600">Address:</span>{profile.address}</li>
                                        <li class="mb-2 mb-xl-3 display-28"><span class="display-26 text-secondary me-2 font-weight-600">Phone number:</span> {profile.phoneNumber}</li>
                                        <li class="mb-2 mb-xl-3 display-28"><span class="display-26 text-secondary me-2 font-weight-600">Email:</span> {profile.email}</li>
                                        <li class="mb-2 mb-xl-3 display-28">
                                            <span class="display-26 text-secondary me-2 font-weight-600">Customer rating: </span>  
                                            
                                        </li>
                                        <b-form-rating v-model={profile.customerRating} variant="warning" class="mb-2"></b-form-rating>

                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <Link to={{pathname: `/company/details/${id}/ads`}}><button className="btn btn-primary">View Ads</button></Link>
            </div>
      )
}

export default RentalCompanyDetails