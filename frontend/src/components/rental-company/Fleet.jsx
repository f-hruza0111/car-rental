import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import Navbar from "../Navbar";
import sendRequest from "../../api/protectedHttpRequsts";



function Fleet(){
    const user_id = sessionStorage.getItem('user_id')
    const url = `/rental-company/${user_id}/fleet`

    const [fleet, setFleet] = useState([]);


    const fetchFleet = async () => {
        const res =  await sendRequest('get', url, "");
    
      
        setFleet(res.data);

         console.log(res);
    
        
    }
    
    
      useEffect(() => {
        fetchFleet()
      }, []);


    return (
        <>
           
            
            <button type="button" class="btn btn-dark" onClick=
            {
                () => {window.location.href = `/rental-company/add-car`}
            }>Add car to your fleet
            
            </button>
            <table class="table table-dark">
            <thead>
            
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Make</th>
                    <th scope="col">Model</th>
                    <th scope="col">Licese plate</th>
                    <th scope="col">Year of production</th>
                    <th scope="col">Registered until</th>
                    <th scope="col">Kilometers</th>
                    <th scope="col">Available</th>
                </tr>
            </thead>
            <tbody>
                {
                    fleet.map(car => (
                        <tr key={car.id}>
                            <th scope="row"><Link to={`/rental-company/${user_id}/fleet/${car.id}`}>{car.id}</Link></th>
                            <td>{car.make}</td>
                            <td>{car.model}</td>
                            <td>{car.licensePlate}</td>                     
                            <td>{car.yearOfProduction}</td>
                            <td>{car.registeredUntil}</td>
                            <td>{car.kilometers}</td>
                            <td>{car.available ? 'Yes' : 'No'}</td>

                        </tr>
                    ))
                }
            </tbody>
            
        
    </table>
        
        </>
    )  
}

export default Fleet