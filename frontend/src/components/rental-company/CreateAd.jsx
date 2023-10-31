import { useEffect, useState } from "react";
import handleUpdate from "../../api/handleUpdate";
import sendRequest from "../../api/protectedHttpRequsts";

function CreateAd() {
    const user_id = sessionStorage.getItem('user_id')
    const postUrl = '/rental-company/'+ user_id + 'create-ad'
    const fleetUrl = '/rental-company/fleet'
    const [fleet, setFleet] = useState([]);


    const fetchFleet = async () => {
        const res =  await sendRequest('get', fleetUrl, "");
    
      
        setFleet(res.data);

         console.log(res);
    
        
    }
    
    
      useEffect(() => {
        fetchFleet()
      }, []);


      console.log(fleet)

    return (  
  
        <>
                <div className='container'>   
                    <form onSubmit={e => handleUpdate(e, postUrl)}>
                        <div class="form-group">
                            <label for="dailyRentingCost">Daily rent cost (in €): </label>
                            <input type="text" class="form-control" id="dailyRentingCost" name='dailyRentingCost'/>

                            <label for="engineType">Car</label>
                            <select name="engineType" class="form-select" >
                                {
                                    fleet.map(car => {
                                        <option key={car.id} value={car.id}>{car.make + " " + car.model + " " + car.yearOfProduction}</option>
                                    })
                                }
                            </select>
                                                                   
                            <button className="btn btn-primary" type="submit">Add car</button>
                        </div>
                    </form> 
                </div>
        </>
      )
}

export default CreateAd