import sendRequst from "../../api/protectedHttpRequsts"
import Navbar from "../Navbar";
import handleUpdate from '../../api/handleUpdate'
import { useState, useEffect } from "react";


function CustomerProfile(){
    
    const [profile, setProfile] = useState([]);
    const user_id = sessionStorage.getItem('user_id')
    
    const url = `/customer/${user_id}`

    const fetchAds = async () => {
        const res = await sendRequst('get', url, "");
    
        //  console.log(res);
        setProfile(res.data);
        
        
    
        //  console.log(totalPages);
        //  console.log(res.data.totalPages)
        //  console.log(res.data.totalPages)
    
        //  console.log(res);
    
        
      }
    
    
      useEffect(() => {
         fetchAds()
      }, []);

  return (  
  
    <>
            <Navbar/>
            <div className='container'>   
                <form onSubmit={e => handleUpdate(e, url)}>
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email" name='email' defaultValue={profile.email} disabled/>

                        <label for="firstName">First name</label>
                        <input type="text" class="form-control" id="firstName" name='firstName' defaultValue={profile.firstName}/>

                        <label for="firstName">Last name</label>
                        <input type="text" class="form-control" id="lastName" name='lastName' defaultValue={profile.lastName}/>

                        <label for="firstName">Address</label>
                        <input type="text" class="form-control" id="address" name='address' defaultValue={profile.address}/>

                        <button className="btn btn-primary" type="submit">Update</button>
                    </div>
                </form> 
            </div>
    </>
  )
}


export default CustomerProfile